package com.carwash.userservice.service;


import com.carwash.userservice.dto.UpdatePasswordRequestDTO;
import com.carwash.userservice.dto.UserRequestDTO;
import com.carwash.userservice.dto.UserResponseDTO;
import com.carwash.userservice.utility.Role;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserByEmail(String email);
    boolean authenticateUser(String email, String password);
    boolean updatePassword(String email, UpdatePasswordRequestDTO updatePasswordRequestDTO);
    void updateRole(String email, Role role);
    UserResponseDTO deleteUser(String email);


}
