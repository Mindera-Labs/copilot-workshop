package io.avios;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;

@Path("/api/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @GET
    public List<Todo> getAllTodos() {
        return Todo.listAll();
    }

    @GET
    @Path("/{id}")
    public Todo getTodo(@PathParam("id") Long id) {
        Todo todo = Todo.findById(id);
        if (todo == null) {
            throw new WebApplicationException("Todo with id " + id + " not found", Status.NOT_FOUND);
        }
        return todo;
    }

    @POST
    @Transactional
    public Response createTodo(Todo todo) {
        if (todo.id != null) {
            throw new WebApplicationException("Todo ID should not be provided", Status.BAD_REQUEST);
        }
        
        todo.persist();
        return Response.status(Status.CREATED).entity(todo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Todo updateTodo(@PathParam("id") Long id, Todo updatedTodo) {
        Todo todo = Todo.findById(id);
        if (todo == null) {
            throw new WebApplicationException("Todo with id " + id + " not found", Status.NOT_FOUND);
        }
        
        todo.title = updatedTodo.title;
        todo.description = updatedTodo.description;
        todo.completed = updatedTodo.completed;
        
        return todo;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTodo(@PathParam("id") Long id) {
        Todo todo = Todo.findById(id);
        if (todo == null) {
            throw new WebApplicationException("Todo with id " + id + " not found", Status.NOT_FOUND);
        }
        
        todo.delete();
        return Response.status(Status.NO_CONTENT).build();
    }
    
    @PATCH
    @Path("/{id}/complete")
    @Transactional
    public Todo markTodoAsCompleted(@PathParam("id") Long id) {
        Todo todo = Todo.findById(id);
        if (todo == null) {
            throw new WebApplicationException("Todo with id " + id + " not found", Status.NOT_FOUND);
        }
        
        todo.completed = true;
        return todo;
    }
}