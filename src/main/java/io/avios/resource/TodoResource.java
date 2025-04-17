package io.avios.resource;

import io.avios.dto.TodoDTO;
import io.avios.service.TodoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;
import java.util.Map;

@Path("/api/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    TodoService todoService;

    @GET
    public Response getAllTodos() {
        List<TodoDTO> todos = todoService.findAllTodos();
        return Response.ok(todos).build();
    }

    @GET
    @Path("/{id}")
    public Response getTodo(@PathParam("id") Long id) {
        return todoService.findTodoById(id)
                .map(todo -> Response.ok(todo).build())
                .orElse(Response.status(Status.NOT_FOUND)
                        .entity(Map.of("message", "Todo with id " + id + " not found"))
                        .build());
    }

    @POST
    public Response createTodo(@Valid TodoDTO todo) {
        TodoDTO created = todoService.createTodo(todo);
        return Response.status(Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTodo(@PathParam("id") Long id, @Valid TodoDTO updatedTodo) {
        return todoService.updateTodo(id, updatedTodo)
                .map(todo -> Response.ok(todo).build())
                .orElse(Response.status(Status.NOT_FOUND)
                        .entity(Map.of("message", "Todo with id " + id + " not found"))
                        .build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodo(@PathParam("id") Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return Response.status(Status.NO_CONTENT).build();
        } else {
            return Response.status(Status.NOT_FOUND)
                    .entity(Map.of("message", "Todo with id " + id + " not found"))
                    .build();
        }
    }
    
    @PATCH
    @Path("/{id}/complete")
    public Response markTodoAsCompleted(@PathParam("id") Long id) {
        return todoService.markTodoAsCompleted(id)
                .map(todo -> Response.ok(todo).build())
                .orElse(Response.status(Status.NOT_FOUND)
                        .entity(Map.of("message", "Todo with id " + id + " not found"))
                        .build());
    }
}