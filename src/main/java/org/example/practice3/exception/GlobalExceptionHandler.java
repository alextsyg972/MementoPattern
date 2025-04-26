package org.example.practice3.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception) throws JsonProcessingException {
        CustomErrorMessage customErrorMessage = new CustomErrorMessage(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(objectMapper.writeValueAsString(customErrorMessage), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        constraintViolations.stream().forEach(error -> {
            errors.put("ConstraintViolationException: " + error.getPropertyPath().toString(), error.getMessage());
            response.put("errors", errors);
                });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
