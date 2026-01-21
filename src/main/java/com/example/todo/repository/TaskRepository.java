package com.example.todo.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.todo.model.Task;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Repository
public class TaskRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path file = Paths.get("data/tasks.json");

    List<Task> tasks = new ArrayList<>();
    private long idCounter = 0;

    public TaskRepository() {
        try {
            if (Files.exists(file)) {
                tasks = mapper.readValue(file.toFile(), new TypeReference<List<Task>>() {
                });
                long maxId = 0;
                for (Task t : tasks) {
                    if (t.getId() > maxId) {
                        maxId = t.getId();
                    }
                }
                idCounter = maxId + 1;

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public Task saveTasks(Task task) {
        task.setId(++idCounter);
        tasks.add(task);
        saveToFile();
        return task;
    }

    public Task findId(Long id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;

    }

    public void complete(Long id) {
        Task task = findId(id);
        if (task != null)
            ;
        task.setCompleted(true);
        saveToFile();
    }

    public void delatedById(Long id) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.getId().equals(id)) {
                iterator.remove();
                saveToFile();
                break;
            }
        }
    }

    private void saveToFile() {
        try {
            Files.createDirectories(file.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
