package com.carwash.userservice.dto;

import com.carwash.userservice.utility.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;

}
