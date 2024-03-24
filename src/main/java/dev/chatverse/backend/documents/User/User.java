package dev.chatverse.backend.documents.User;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Nonnull
    private String id;

    @Nonnull
    private String givenName;

    @Nonnull
    private String familyName;

    private String picture;

    private String locale;

    @Nonnull
    private String userName;

    @Nonnull
    @Email
    private String email;

    @Nonnull
    private Set<Role> roles = new HashSet<>();
}