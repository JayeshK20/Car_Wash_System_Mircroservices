package com.carwash.userservice.controller;

import com.carwash.userservice.dto.ResponseDTO;
import com.carwash.userservice.dto.UpdatePasswordRequestDTO;
import com.carwash.userservice.dto.UserRequestDTO;
import com.carwash.userservice.dto.UserResponseDTO;
import com.carwash.userservice.service.UserService;
import com.carwash.userservice.utility.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<String>> welcome() {
        log.info("Welcome to user service");

        ResponseDTO<String> response = new ResponseDTO<>(
                LocalDateTime.now(),
                true,
                "Welcome to CarWash-Microservices",
                "User Section"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> registerUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        log.info("Registering user: {}", userRequestDTO.getEmail());
        UserResponseDTO createdUser = userService.registerUser(userRequestDTO);
        log.info("User registered successfully: {}", createdUser.getEmail());

        ResponseDTO<UserResponseDTO> response = new ResponseDTO<>(
                LocalDateTime.now(),
                true,
                "User registered successfully",
                createdUser
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        log.info("Getting all users");
        List<UserResponseDTO> users = userService.getAllUsers();
        log.info("Users retrieved successfully: {}", users.size());

        ResponseDTO<List<UserResponseDTO>> response = new ResponseDTO<>(
                LocalDateTime.now(),
                true,
                "Users retrieved successfully",
                users
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> getUserByEmail(@PathVariable String email) {
        log.info("Getting user by email: {}", email);
        UserResponseDTO user = userService.getUserByEmail(email);
        log.info("User retrieved successfully: {}", user.getEmail());

        ResponseDTO<UserResponseDTO> response = new ResponseDTO<>(
                LocalDateTime.now(),
                true,
                "User retrieved successfully",
                user
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> loginUser(
            @RequestParam String email,
            @RequestParam String password) {

        log.info("Logging in user: {}", email);
        boolean isAuthenticated = userService.authenticateUser(email, password);

        if (isAuthenticated) {
            log.info("User logged in successfully: {}", email);
        } else {
            log.info("User login failed: {}", email);
        }

        ResponseDTO<String> response = new ResponseDTO<>(
                LocalDateTime.now(),
                isAuthenticated,
                isAuthenticated ? "Login successful" : "Invalid credentials",
                isAuthenticated ? "Welcome back!" : "Authentication failed. Check your username/email and password!"
        );

        return new ResponseEntity<>(response, isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update-password/{email}")
    public ResponseEntity<ResponseDTO<String>> updatePassword(
            @PathVariable String email,
            @Valid @RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {

        log.info("Updating password for user: {}", email);
        boolean isPasswordUpdated = userService.updatePassword(email, updatePasswordRequestDTO);

        if (isPasswordUpdated) {
            log.info("Password updated successfully: {}", email);
        } else {
            log.info("Password update failed: {}", email);
        }

        ResponseDTO<String> response = new ResponseDTO<>(
                LocalDateTime.now(),
                isPasswordUpdated,
                isPasswordUpdated ? "Password updated successfully" : "Password update failed",
                isPasswordUpdated ? "Your new password is now active" : "The old password you entered is incorrect. Please try again."
        );

        return new ResponseEntity<>(response, isPasswordUpdated ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-role/{email}")
    public ResponseEntity<ResponseDTO<String>> updateRole(
            @PathVariable String email,
            @RequestParam Role role) {

        log.info("Updating role for user: {}", email);
        userService.updateRole(email, role);
        log.info("Role updated successfully: {}", email);

        ResponseDTO<String> response = new ResponseDTO<>(
                LocalDateTime.now(),
                true,
                "Role updated successfully",
                "Your new role is now active"
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> deleteUser(@PathVariable String email) {
        log.info("Deleting user: {}", email);
        UserResponseDTO deletedUser = userService.deleteUser(email);
        log.info("User deleted successfully: {}", email);

        ResponseDTO<UserResponseDTO> response = new ResponseDTO<>(
                LocalDateTime.now(),
                true,
                "User deleted successfully",
                deletedUser
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
