package com.example.file.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private int code;
    private String message;
    private HttpStatus status;
    private Map<String, String> errors;
    private Object data;

    public ApiResponse(int i, String validationFailed, HttpStatus httpStatus, Map<String, String> errors) {
        this.code = i;
        this.message = validationFailed;
        this.status = httpStatus;
        this.errors = errors;
    }
}
