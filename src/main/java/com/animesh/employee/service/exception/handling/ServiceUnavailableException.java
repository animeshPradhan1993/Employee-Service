package com.animesh.employee.service.exception.handling;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RuntimeException {
    private HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
    private String message;

    public ServiceUnavailableException(String message) {
        this.message = message;
    }
}
