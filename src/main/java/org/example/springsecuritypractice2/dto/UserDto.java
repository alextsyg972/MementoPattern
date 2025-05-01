package org.example.springsecuritypractice2.dto;

import lombok.Builder;

@Builder
public record UserDto(String username, String role) {
}
