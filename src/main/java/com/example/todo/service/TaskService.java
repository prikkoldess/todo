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
        return repository.saveTasks(task);
    }

    public List<Task> getALL() {
        return repository.findAll();
    }

    public void completedTask(Long id) {
        repository.complete(id);
    }

    public void delated(Long id) {
        repository.delatedById(id);
    }

}
