package dev.chatverse.backend.documents.User;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String pictureUrl;
    private Set<Role> roles = new HashSet<>();
}