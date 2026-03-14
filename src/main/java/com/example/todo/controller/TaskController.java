package com.example.todo.controller;

import com.example.todo.service.TaskService;
import com.example.todo.model.TaskCreateDto;
import com.example.todo.model.TaskDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(service.getById(id, principal.getName()));
    }

    @GetMapping("/all tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(Principal principal) {

        return ResponseEntity.ok(service.getAll(principal.getName()));
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<List<TaskDto>> getUncompletedTasks(Principal principal) {
        return ResponseEntity.ok(service.uncompletedTasks(principal.getName()));
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<TaskDto>> getTasksByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getUserTasks(username));
    }

    @PostMapping("/create task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreateDto requestDto, Principal principal) {
        TaskDto createdTask = service.createTask(requestDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> completedTask(@PathVariable Long id, Principal principal) {
        service.completedTask(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Principal principal) {
        service.deleteTask(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

}
