package io.avios;

import io.avios.dto.TodoDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@QuarkusTestResource(PostgresqlResource.class)
public class TodoResourceIT {

    @Test
    public void testGetAllTodos() {
        given()
            .when().get("/api/todos")
            .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(0));
    }

    @Test
    public void testCreateAndGetTodo() {
        // Create a new todo
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.title = "Test Todo";
        todoDTO.description = "Test Description";
        todoDTO.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todoDTO)
            .when().post("/api/todos")
            .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Get the created todo
        given()
            .when().get("/api/todos/" + todoId)
            .then()
                .statusCode(200)
                .body("title", equalTo("Test Todo"))
                .body("description", equalTo("Test Description"))
                .body("completed", equalTo(false));
    }

    @Test
    public void testUpdateTodo() {
        // Create a todo first
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.title = "Original Title";
        todoDTO.description = "Original Description";
        todoDTO.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todoDTO)
            .when().post("/api/todos")
            .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Update the todo
        TodoDTO updatedTodoDTO = new TodoDTO();
        updatedTodoDTO.id = todoId; // Include ID for update
        updatedTodoDTO.title = "Updated Title";
        updatedTodoDTO.description = "Updated Description";
        updatedTodoDTO.completed = true;

        given()
            .contentType(ContentType.JSON)
            .body(updatedTodoDTO)
            .when().put("/api/todos/" + todoId)
            .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("description", equalTo("Updated Description"))
                .body("completed", equalTo(true));
    }

    @Test
    public void testDeleteTodo() {
        // Create a todo first
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.title = "Delete Me";
        todoDTO.description = "To be deleted";
        todoDTO.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todoDTO)
            .when().post("/api/todos")
            .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Delete the todo
        given()
            .when().delete("/api/todos/" + todoId)
            .then()
                .statusCode(204);

        // Verify it's gone
        given()
            .when().get("/api/todos/" + todoId)
            .then()
                .statusCode(404);
    }

    @Test
    public void testMarkTodoAsCompleted() {
        // Create a todo first
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.title = "Complete Me";
        todoDTO.description = "To be completed";
        todoDTO.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todoDTO)
            .when().post("/api/todos")
            .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Mark as completed
        given()
            .when().patch("/api/todos/" + todoId + "/complete")
            .then()
                .statusCode(200)
                .body("completed", equalTo(true));
    }
    
    @Test
    public void testCreateTodoWithInvalidData() {
        // Test with empty title (which should be rejected by validation)
        TodoDTO invalidTodoDTO = new TodoDTO();
        invalidTodoDTO.title = ""; // Empty title should be rejected
        invalidTodoDTO.description = "Test Description";
        
        given()
            .contentType(ContentType.JSON)
            .body(invalidTodoDTO)
            .when().post("/api/todos")
            .then()
                .statusCode(400); // Bad request due to validation failure
    }
}