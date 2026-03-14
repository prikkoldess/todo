package com.example.todo.repository;

import com.example.todo.model.Task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserUsername(String username);

    Optional<Task> findByIdAndUserUsername(Long id, String username);
}