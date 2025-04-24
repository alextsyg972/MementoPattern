package org.example.practice1.exception;

import java.time.LocalDateTime;

public record CustomErrorMessage(String message, LocalDateTime localDateTime) {
}
