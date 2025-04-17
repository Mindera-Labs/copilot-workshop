package io.avios.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Todo extends PanacheEntity {
    
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    @Column(length = 100, nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private boolean completed;
    
    // Default constructor required by JPA
    public Todo() {
    }
    
    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
    
    // Getters and setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}