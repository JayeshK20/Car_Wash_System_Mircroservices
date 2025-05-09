package com.carwash.userservice.service.impl;

import com.carwash.userservice.dto.UpdatePasswordRequestDTO;
import com.carwash.userservice.dto.UserRequestDTO;
import com.carwash.userservice.dto.UserResponseDTO;
import com.carwash.userservice.entity.User;
import com.carwash.userservice.exception.ResourceNotFoundException;
import com.carwash.userservice.repository.UserRepository;
import com.carwash.userservice.service.UserService;
import com.carwash.userservice.utility.Role;
import com.carwash.userservice.utility.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("User with email " + userRequestDTO.getEmail() + " already exists.");
        }

        User user = UserMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }

        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
        return UserMapper.toDTO(user);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean updatePassword(String email, UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));

        if (passwordEncoder.matches(updatePasswordRequestDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword()));
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void updateRole(String email, Role role) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserResponseDTO deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
        UserResponseDTO deletedUser = UserMapper.toDTO(user);
        userRepository.delete(user);
        return deletedUser;
    }
}
