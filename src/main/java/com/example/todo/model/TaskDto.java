package com.example.todo.model;

import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String description;
    private boolean completed;
}
