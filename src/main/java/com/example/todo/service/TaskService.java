package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todo.model.Task;
import com.example.todo.model.TaskCreateDto;
import com.example.todo.model.TaskDto;
import com.example.todo.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskDto createTask(TaskCreateDto dto) {
        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setCompleted(false);
        Task seveTask = repository.save(task);
        return mapToDto(seveTask);
    }

    public List<TaskDto> getAll() {

        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    public TaskDto getById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task ID " + id + " not found"));
        return mapToDto(task);

    }

    public void completedTask(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        task.setCompleted(true);
        repository.save(task);

    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        return dto;

    }
}
