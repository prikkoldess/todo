package com.example.todo.controller;

import com.example.todo.service.TaskService;
import com.example.todo.model.Task;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getALL();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.createTask(task);
    }

    @PutMapping("/{id}/complete")
    public void completeTask(@PathVariable Long id) {
        service.completedTask(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.delated(id);
    }
}
