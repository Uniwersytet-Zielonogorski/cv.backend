package dev.chatverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final String id;
    private final String email;
    private final String username;
}
