package dev.chatverse.backend.dto;

import dev.chatverse.backend.documents.User.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Set;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final ObjectId id;
    private final String email;
    private final String userName;
    private final String pictureUrl;
    private final Set<Role> roles;
}


