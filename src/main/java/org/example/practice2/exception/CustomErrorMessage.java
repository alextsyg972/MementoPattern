package org.example.practice2.exception;

import java.time.LocalDateTime;

public record CustomErrorMessage(String message, LocalDateTime localDateTime) {
}
