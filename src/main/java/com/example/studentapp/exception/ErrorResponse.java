package com.example.studentapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private int statusCode;

    public ErrorResponse(int value, String message) {
        super();
        this.status = HttpStatus.valueOf(value);
        this.message = message;
    }
}
