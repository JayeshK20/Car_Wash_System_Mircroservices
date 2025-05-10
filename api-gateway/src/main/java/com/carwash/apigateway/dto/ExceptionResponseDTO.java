package com.carwash.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponseDTO {

    private LocalDateTime timestamp;
    private String message;
    private Object details;
}

