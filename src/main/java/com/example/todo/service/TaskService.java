package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todo.model.Role;
import com.example.todo.model.Task;
import com.example.todo.model.TaskCreateDto;
import com.example.todo.model.TaskDto;
import com.example.todo.model.User;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public TaskDto createTask(TaskCreateDto dto, String username) {
        User user = getCurrentUser(username);

        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setCompleted(false);
        task.setUser(user);
        Task seveTask = repository.save(task);
        return mapToDto(seveTask);
    }

    public List<TaskDto> getAll(String username) {
        User currentUser = getCurrentUser(username);

        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            return repository.findAll().stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        return repository.findByUserUsername(username).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    public List<TaskDto> uncompletedTasks(String uername) {
        return repository.findByUserUsername(uername).stream()
                .filter(t -> t.isCompleted() == false)
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    public List<TaskDto> getUserTasks(String username) {
        return repository.findByUserUsername(username).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TaskDto getById(Long id, String username) {

        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task ID " + id + " not found"));

        User user = getCurrentUser(username);
        if (user.getRole() != Role.ROLE_ADMIN && !task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied: You are not the owner of this task");
        }

        return mapToDto(task);

    }

    public void completedTask(Long id, String username) {
        Task task = repository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        task.setCompleted(true);
        repository.save(task);

    }

    public void deleteTask(Long id, String username) {
        Task task = repository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new RuntimeException("Task not found or access denied"));
        repository.delete(task);
    }

    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.isCompleted());
        return dto;

    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}
