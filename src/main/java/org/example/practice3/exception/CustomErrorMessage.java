package org.example.practice3.exception;

import java.time.LocalDateTime;

public record CustomErrorMessage(String message, LocalDateTime localDateTime) {
}
