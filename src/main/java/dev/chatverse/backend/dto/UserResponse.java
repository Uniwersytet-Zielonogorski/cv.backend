package dev.chatverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final ObjectId id;
    private final String email;
    private final String username;
    private final String pictureUrl;
}


