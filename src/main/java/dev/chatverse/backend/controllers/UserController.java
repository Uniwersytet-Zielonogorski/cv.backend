package dev.chatverse.backend.controllers;

import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/me")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public UserResponse me(Principal principal) {
        String currentPrincipalName = principal.getName();
        return userService.getUserResponse(currentPrincipalName);
    }
}
