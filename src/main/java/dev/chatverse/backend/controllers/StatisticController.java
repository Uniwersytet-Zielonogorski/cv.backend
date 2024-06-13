package dev.chatverse.backend.controllers;

import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.services.StatisticService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.chatverse.backend.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RequestMapping("/statistics")
@AllArgsConstructor
@RestController
public class StatisticController {

    private UserService userService;
    private StatisticService StatisticService;

    @GetMapping("/me")
    public StatisticResponse statistic(Principal principal) {
        String currentPrincipalName = principal.getName();
        return userService.getStatisticResponse(currentPrincipalName);
    }

    @GetMapping("/all")
    @RolesAllowed("ADMIN")
    public Set<StatisticResponse> getAllUsers() {
        return StatisticService.getAllUsersStatistics();
    }

    @GetMapping("/leaderboard")
    public List<StatisticResponse> getLeaderboard() {
        return StatisticService.getLeaderboard();
    }

}
