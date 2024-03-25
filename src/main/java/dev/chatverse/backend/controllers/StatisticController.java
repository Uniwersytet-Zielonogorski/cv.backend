package dev.chatverse.backend.controllers;

import dev.chatverse.backend.documents.User.User;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.chatverse.backend.services.UserService;


@RestController
public class StatisticController {

    private UserService userService;

    @Autowired
    public StatisticController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/statistic")
    @RolesAllowed("ROLE_ADMIN")
    String statistic () {
        OAuth2User oAuth2User = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = oAuth2User.getAttribute("email");
        User user = userService.loadUserByEmail(email);
        return user.getFamilyName();
    }



}
