package com.example.todo.service;

import org.springframework.stereotype.Service;

import com.example.todo.model.Role;
import com.example.todo.model.User;
import com.example.todo.model.UserCreateDto;
import com.example.todo.model.UserDto;
import com.example.todo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerUser(UserCreateDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.ROLE_USER);
        User saveUser = userRepository.save(user);
        return mapToDto(saveUser);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User username " + username + " not found"));
        return mapToDto(user);

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
