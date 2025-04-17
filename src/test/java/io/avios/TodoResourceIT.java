package io.avios;

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
        Todo todo = new Todo();
        todo.title = "Test Todo";
        todo.description = "Test Description";
        todo.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todo)
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
        Todo todo = new Todo();
        todo.title = "Original Title";
        todo.description = "Original Description";
        todo.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todo)
            .when().post("/api/todos")
            .then()
                .statusCode(201)
                .extract().jsonPath().getLong("id");

        // Update the todo
        todo.title = "Updated Title";
        todo.description = "Updated Description";
        todo.completed = true;

        given()
            .contentType(ContentType.JSON)
            .body(todo)
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
        Todo todo = new Todo();
        todo.title = "Delete Me";
        todo.description = "To be deleted";
        todo.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todo)
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
        Todo todo = new Todo();
        todo.title = "Complete Me";
        todo.description = "To be completed";
        todo.completed = false;

        Long todoId = given()
            .contentType(ContentType.JSON)
            .body(todo)
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
}