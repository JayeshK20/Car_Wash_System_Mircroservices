package com.carwash.apigateway.dto;

import com.carwash.apigateway.utility.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserClientDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
