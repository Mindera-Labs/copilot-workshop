package io.avios;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Todo extends PanacheEntity {
    
    @Column(length = 100)
    public String title;
    
    public String description;
    
    public boolean completed;
    
    public Todo() {
    }
    
    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
}