package io.avios.repository;

import io.avios.entity.Todo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TodoRepository implements PanacheRepository<Todo> {
    
    public List<Todo> findAllTodos() {
        return listAll();
    }
    
    public Optional<Todo> findTodoById(Long id) {
        return findByIdOptional(id);
    }
    
    public void createTodo(Todo todo) {
        persist(todo);
    }
    
    public boolean deleteTodoById(Long id) {
        return deleteById(id);
    }
    
    public List<Todo> findCompletedTodos() {
        return list("completed", true);
    }
    
    public List<Todo> findPendingTodos() {
        return list("completed", false);
    }
}