package dev.chatverse.backend.controllers;

import dev.chatverse.backend.dto.StatisticResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.chatverse.backend.services.UserService;

import java.security.Principal;

@RequestMapping("/statistic")
@AllArgsConstructor
@RestController
public class StatisticController {

    private UserService userService;


    @GetMapping("/me")
    public StatisticResponse statistic(Principal principal) {

        String currentPrincipalName = principal.getName();

        return userService.getStatisticResponse(currentPrincipalName);
    }
}
