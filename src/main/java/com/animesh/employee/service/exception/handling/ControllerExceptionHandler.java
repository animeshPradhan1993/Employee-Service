package com.animesh.employee.service.exception.handling;

import com.animesh.generated.employee.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleAPiException(ApiException ex, WebRequest request) {
        HttpStatus httpStatus;
        String message;
        switch (ex.getCode()) {
            case 404 -> {
                httpStatus = HttpStatus.NOT_FOUND;
                message = StringUtils.substringBetween(ex.getMessage(), "\"message\":\"", "\",\"details\":");
            }
            case 400 -> {
                httpStatus = HttpStatus.BAD_REQUEST;
                message = StringUtils.substringBetween(ex.getMessage(), "\"message\":\"", "\",\"details\":");
            }


            default -> throw new IllegalStateException("Unexpected value: " + ex.getCode());
        }


        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, httpStatus);
    }


    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> serviceUnavailableException(ServiceUnavailableException ex, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgumentException(MethodArgumentNotValidException ex, WebRequest request) {
        String[] defaultmessageArray = ex.getMessage().split(";");
        String message = StringUtils.substringBetween(defaultmessageArray[defaultmessageArray.length - 1], "default message [", "]] ");
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingParamsException(MissingRequestHeaderException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<?> handleGlobalException(AuthorizationDeniedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> authorizationDenied(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
