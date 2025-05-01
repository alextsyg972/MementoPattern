package org.example.springsecuritypractice2.exception;

public class NotLockedException extends RuntimeException {
    public NotLockedException(String message) {
        super(message);
    }
}
