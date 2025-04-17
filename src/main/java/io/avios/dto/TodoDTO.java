package io.avios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoDTO {

    public Long id;
    
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    public String title;
    
    public String description;
    
    public boolean completed;
    
    public TodoDTO() {
    }
    
    public TodoDTO(Long id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }
}