package org.example.practice3.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private ObjectMapper objectMapper;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        globalExceptionHandler = new GlobalExceptionHandler(objectMapper);
    }

    @Test
    void handleResourceNotFound() throws Exception {
        objectMapper.findAndRegisterModules();
        String message = "Product not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);
        ResponseEntity<String> response = globalExceptionHandler.handleResourceNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        CustomErrorMessage errorMessage = objectMapper.readValue(response.getBody(), CustomErrorMessage.class);
        assertEquals(message, errorMessage.message());
        assertNotNull(errorMessage.localDateTime());
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path mockPath = mock(Path.class);

        when(mockPath.toString()).thenReturn("customer.name");
        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn("must not be null");

        Set<ConstraintViolation<?>> violations = Set.of(violation);
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        ResponseEntity<Object> response = globalExceptionHandler.handleMethodArgumentNotValid(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertTrue(responseBody.containsKey("errors"));
    }
}