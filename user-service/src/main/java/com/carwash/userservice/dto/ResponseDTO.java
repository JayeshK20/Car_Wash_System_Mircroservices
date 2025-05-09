package com.carwash.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private T data;
}
