package com.example.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo.model.Role;
import com.example.todo.model.Task;
import com.example.todo.model.TaskCreateDto;
import com.example.todo.model.TaskDto;
import com.example.todo.model.User;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.TaskService;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TaskService taskService;

    private User user;
    private User admin;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setId(1L);
        user.setRole(Role.ROLE_USER);

        admin = new User();
        admin.setUsername("admin");
        admin.setRole(Role.ROLE_ADMIN);

        task = new Task();
        task.setDescription("Create Task");
        task.setId(1L);
        task.setCompleted(false);
        task.setUser(user);

    }

    @Test
    void testCreateTask() {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setDescription("Create Task");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(a -> a.getArgument(0));

        TaskDto result = taskService.createTask(dto, "testuser");

        assertNotNull(result);
        assertEquals("Create Task", result.getDescription());
        assertFalse(result.isCompleted());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTasksForAdmin() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDto> result = taskService.getAll("admin");

        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTasksForUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.findByUserUsername("testuser")).thenReturn(List.of(task));

        List<TaskDto> result = taskService.getAll("testuser");

        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findByUserUsername("testuser");
    }

    @Test
    void testGetByID() {

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDto result = taskService.getById(1L, "testuser");

        assertNotNull(result);
        assertEquals("Create Task", result.getDescription());
    }

    @Test
    void testCompletedTask() {
        when(taskRepository.findByIdAndUserUsername(1L, "testuser")).thenReturn(Optional.of(task));

        taskService.completedTask(1L, "testuser");

        assertTrue(task.isCompleted());
        verify(taskRepository, times(1)).save(task);
    }

}
