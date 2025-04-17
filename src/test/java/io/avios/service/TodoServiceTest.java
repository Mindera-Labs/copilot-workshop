package io.avios.service;

import io.avios.dto.TodoDTO;
import io.avios.entity.Todo;
import io.avios.repository.TodoRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TodoServiceTest {

    @Inject
    TodoService todoService;

    @InjectMock
    TodoRepository todoRepository;

    @Test
    public void testFindAllTodos() {
        // Setup mock data
        Todo todo1 = new Todo();
        todo1.id = 1L;
        todo1.setTitle("Test Todo 1");
        todo1.setDescription("Description 1");
        todo1.setCompleted(false);
        
        Todo todo2 = new Todo();
        todo2.id = 2L;
        todo2.setTitle("Test Todo 2");
        todo2.setDescription("Description 2");
        todo2.setCompleted(true);
        
        List<Todo> todos = Arrays.asList(todo1, todo2);
        when(todoRepository.findAllTodos()).thenReturn(todos);
        
        // Test the service method
        List<TodoDTO> result = todoService.findAllTodos();
        
        // Assertions
        assertEquals(2, result.size());
        assertEquals("Test Todo 1", result.get(0).title);
        assertEquals("Test Todo 2", result.get(1).title);
        assertFalse(result.get(0).completed);
        assertTrue(result.get(1).completed);
    }
    
    @Test
    public void testFindTodoById() {
        // Setup mock data
        Todo todo = new Todo();
        todo.id = 1L;
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);
        
        when(todoRepository.findTodoById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.findTodoById(2L)).thenReturn(Optional.empty());
        
        // Test found case
        Optional<TodoDTO> foundResult = todoService.findTodoById(1L);
        assertTrue(foundResult.isPresent());
        assertEquals("Test Todo", foundResult.get().title);
        
        // Test not found case
        Optional<TodoDTO> notFoundResult = todoService.findTodoById(2L);
        assertFalse(notFoundResult.isPresent());
    }
    
    @Test
    public void testCreateTodo() {
        // Setup mock
        Mockito.doAnswer(invocation -> {
            Todo entity = invocation.getArgument(0);
            entity.id = 1L;
            return null;
        }).when(todoRepository).createTodo(any(Todo.class));
        
        // Test the service method
        TodoDTO dto = new TodoDTO();
        dto.title = "New Todo";
        dto.description = "New Description";
        
        TodoDTO result = todoService.createTodo(dto);
        
        // Assertions
        assertNotNull(result.id);
        assertEquals("New Todo", result.title);
        assertEquals("New Description", result.description);
        assertFalse(result.completed);
    }
    
    @Test
    public void testCreateTodoWithId() {
        // Test that creating a todo with an ID throws an exception
        TodoDTO dto = new TodoDTO();
        dto.id = 1L;
        dto.title = "Illegal Todo";
        
        assertThrows(WebApplicationException.class, () -> {
            todoService.createTodo(dto);
        });
    }
    
    @Test
    public void testUpdateTodo() {
        // Setup mock data
        Todo existingTodo = new Todo();
        existingTodo.id = 1L;
        existingTodo.setTitle("Original Title");
        existingTodo.setDescription("Original Description");
        existingTodo.setCompleted(false);
        
        when(todoRepository.findTodoById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.findTodoById(2L)).thenReturn(Optional.empty());
        
        // Test valid update
        TodoDTO updateDto = new TodoDTO();
        updateDto.id = 1L;
        updateDto.title = "Updated Title";
        updateDto.description = "Updated Description";
        updateDto.completed = true;
        
        Optional<TodoDTO> updateResult = todoService.updateTodo(1L, updateDto);
        
        assertTrue(updateResult.isPresent());
        assertEquals("Updated Title", updateResult.get().title);
        assertEquals("Updated Description", updateResult.get().description);
        assertTrue(updateResult.get().completed);
        
        // Test update for non-existent todo
        Optional<TodoDTO> notFoundResult = todoService.updateTodo(2L, updateDto);
        assertFalse(notFoundResult.isPresent());
    }
    
    @Test
    public void testDeleteTodo() {
        when(todoRepository.deleteTodoById(1L)).thenReturn(true);
        when(todoRepository.deleteTodoById(2L)).thenReturn(false);
        
        assertTrue(todoService.deleteTodo(1L));
        assertFalse(todoService.deleteTodo(2L));
    }
    
    @Test
    public void testMarkTodoAsCompleted() {
        // Setup mock data
        Todo todo = new Todo();
        todo.id = 1L;
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);
        
        when(todoRepository.findTodoById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.findTodoById(2L)).thenReturn(Optional.empty());
        
        // Test marking existing todo as completed
        Optional<TodoDTO> completedResult = todoService.markTodoAsCompleted(1L);
        
        assertTrue(completedResult.isPresent());
        assertTrue(completedResult.get().completed);
        
        // Test marking non-existent todo as completed
        Optional<TodoDTO> notFoundResult = todoService.markTodoAsCompleted(2L);
        assertFalse(notFoundResult.isPresent());
    }
}
