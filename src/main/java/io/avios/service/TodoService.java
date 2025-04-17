package io.avios.service;

import io.avios.dto.TodoDTO;
import io.avios.entity.Todo;
import io.avios.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TodoService {

    @Inject
    TodoRepository repository;

    public List<TodoDTO> findAllTodos() {
        return repository.findAllTodos().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TodoDTO> findTodoById(Long id) {
        return repository.findTodoById(id)
                .map(this::mapToDTO);
    }

    @Transactional
    public TodoDTO createTodo(TodoDTO dto) {
        if (dto.id != null) {
            throw new WebApplicationException("Todo ID should not be provided", Status.BAD_REQUEST);
        }

        Todo entity = new Todo();
        entity.setTitle(dto.title);
        entity.setDescription(dto.description);
        entity.setCompleted(false);

        repository.createTodo(entity);
        return mapToDTO(entity);
    }

    @Transactional
    public Optional<TodoDTO> updateTodo(Long id, TodoDTO dto) {
        return repository.findTodoById(id)
                .map(entity -> {
                    entity.setTitle(dto.title);
                    entity.setDescription(dto.description);
                    entity.setCompleted(dto.completed);
                    return mapToDTO(entity);
                });
    }

    @Transactional
    public boolean deleteTodo(Long id) {
        return repository.deleteTodoById(id);
    }

    @Transactional
    public Optional<TodoDTO> markTodoAsCompleted(Long id) {
        return repository.findTodoById(id)
                .map(entity -> {
                    entity.setCompleted(true);
                    return mapToDTO(entity);
                });
    }
    
    private TodoDTO mapToDTO(Todo entity) {
        return new TodoDTO(
            entity.id,
            entity.getTitle(),
            entity.getDescription(),
            entity.isCompleted()
        );
    }
    
    private Todo mapToEntity(TodoDTO dto) {
        Todo entity = new Todo();
        entity.setTitle(dto.title);
        entity.setDescription(dto.description);
        entity.setCompleted(dto.completed);
        return entity;
    }
}