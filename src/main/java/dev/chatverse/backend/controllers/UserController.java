package dev.chatverse.backend.controllers;

import dev.chatverse.backend.Forms.EditUserForm;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/me")
    public UserResponse me(Principal principal) {
        String currentPrincipalName = principal.getName();
        return userService.getUserResponse(currentPrincipalName);
    }

    @PutMapping("/me")
    public UserResponse updateMe(
            @RequestBody EditUserForm editUserForm,
            Principal principal
    ) {
        String currentPrincipalName = principal.getName();
        return userService.updateUser(
                currentPrincipalName,
                editUserForm.getGivenName(),
                editUserForm.getFamilyName(),
                editUserForm.getUserName(),
                editUserForm.getPicture(),
                java.util.Optional.empty()
        );
    }

    @DeleteMapping("/me")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMe(Principal principal) {
        String currentPrincipalName = principal.getName();
        userService.deleteUser(currentPrincipalName);
    }

    @GetMapping("/all")
    public Set<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserResponse(email);
    }

    @PostMapping("/pay")
    public UserResponse payForUnban(Principal principal) {
        String currentPrincipalName = principal.getName();
        return userService.unbanUser(currentPrincipalName);
    }

    @RolesAllowed("ADMIN")
    @PutMapping("/{email}")
    public UserResponse updateUser(
            @PathVariable String email,
            @RequestBody EditUserForm editUserForm
    ) {
        System.out.println(email);
        return userService.updateUser(
                email,
                editUserForm.getGivenName(),
                editUserForm.getFamilyName(),
                editUserForm.getUserName(),
                editUserForm.getPicture(),
                editUserForm.getRoles()
        );
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/{email}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/{email}/ban")
    public UserResponse banUser(@PathVariable String email) {
        return userService.banUser(email);
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/{email}/unban")
    public UserResponse unbanUser(@PathVariable String email) {
        return userService.unbanUser(email);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/bannedUsers")
    public Set<UserResponse> getBannedUsers() {
        return userService.getBannedUsers();
    }
}
