package dev.chatverse.backend.controllers;

import dev.chatverse.backend.Forms.EditUserForm;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@ApiResponse(responseCode = "401", description = "Unauthorized.")
public class UserController {

    private UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get the current user.")
    @ApiResponse(responseCode = "200", description = "The current user.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    public UserResponse me(Principal principal) {
        String currentPrincipalName = principal.getName();
        return userService.getUserResponse(currentPrincipalName);
    }

    @PutMapping("/me")
    @Operation(summary = "Update the current user.")
    @ApiResponse(responseCode = "200", description = "The updated current user.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    public UserResponse updateMe(
            @Parameter(description = "The updated user information.", required = true) @RequestBody EditUserForm editUserForm,
            @Parameter(hidden = true) Principal principal
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
    @Operation(summary = "Delete the current user.")
    @ApiResponse(responseCode = "204", description = "The current user has been deleted.")
    public void deleteMe(Principal principal) {
        String currentPrincipalName = principal.getName();
        userService.deleteUser(currentPrincipalName);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users.")
    @ApiResponse(responseCode = "200", description = "All users.", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
            )
    })
    public Set<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{email}")
    @Operation(summary = "Get the user with the specified email.")
    @ApiResponse(responseCode = "200", description = "The user with the specified email.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.getUserResponse(email);
    }

    @PostMapping("/pay")
    @Operation(summary = "Pay for unban.")
    @ApiResponse(responseCode = "200", description = "The current user has been unbanned.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    public UserResponse payForUnban(Principal principal) {
        String currentPrincipalName = principal.getName();
        return userService.unbanUser(currentPrincipalName);
    }

    @RolesAllowed("ADMIN")
    @PutMapping("/{email}")
    @Operation(summary = "Update the user with the specified email.")
    @ApiResponse(responseCode = "200", description = "The updated user with the specified email.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
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
    @Operation(summary = "Delete the user with the specified email.")
    @ApiResponse(responseCode = "204", description = "The user with the specified email has been deleted.")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/{email}/ban")
    @Operation(summary = "Ban the user with the specified email.")
    @ApiResponse(responseCode = "200", description = "The user with the specified email has been banned.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    public UserResponse banUser(@PathVariable String email) {
        return userService.banUser(email);
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/{email}/unban")
    @Operation(summary = "Unban the user with the specified email.")
    @ApiResponse(responseCode = "200", description = "The user with the specified email has been unbanned.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    public UserResponse unbanUser(@PathVariable String email) {
        return userService.unbanUser(email);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/bannedUsers")
    @Operation(summary = "Get all banned users.")
    @ApiResponse(responseCode = "200", description = "All banned users.", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
            )
    })
    public Set<UserResponse> getBannedUsers() {
        return userService.getBannedUsers();
    }
}
