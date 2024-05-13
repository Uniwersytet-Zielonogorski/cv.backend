package dev.chatverse.backend.dto;

import dev.chatverse.backend.documents.User.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final String id;
    private final String email;
    private final String userName;
    private final String pictureUrl;
    private final Set<Role> roles;
}


