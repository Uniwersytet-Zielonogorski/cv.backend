package dev.chatverse.backend.Forms;

import dev.chatverse.backend.documents.User.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class EditUserForm {
    @Email(message = "Email should be valid")
    private String email;
    private String givenName;
    private String familyName;
    private String userName;
    private String picture;
    private Set<Role> roles;

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getGivenName() {
        return Optional.ofNullable(givenName);
    }

    public Optional<String> getFamilyName() {
        return Optional.ofNullable(familyName);
    }

    public Optional<String> getUserName() {
        return Optional.ofNullable(userName);
    }

    public Optional<String> getPicture() {
        return Optional.ofNullable(picture);
    }

    public Optional<Set<Role>> getRoles() {
        return Optional.ofNullable(roles);
    }
}
