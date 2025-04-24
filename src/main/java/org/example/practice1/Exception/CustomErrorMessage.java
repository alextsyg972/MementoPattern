package org.example.practice1.Exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record CustomErrorMessage(String message, LocalDateTime localDateTime) {
}
