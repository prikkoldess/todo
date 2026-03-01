package com.example.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(Task task) {
        task.setCompleted(false);
        return repository.save(task);
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public void completedTask(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        task.setCompleted(true);
        repository.save(task);
    }

    public void deleted(Long id) {
        repository.deleteById(id);
    }

}
