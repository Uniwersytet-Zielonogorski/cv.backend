package dev.chatverse.backend.controllers;

import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.services.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@ApiResponse(responseCode = "401", description = "Unauthorized.")
public class StatisticController {

    private UserService userService;
    private StatisticService StatisticService;

    @GetMapping("/me")
    @Operation(summary = "Get the statistic of the current user.")
    @ApiResponse(responseCode = "200", description = "The statistic of the current user.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StatisticResponse.class))
    })
    public StatisticResponse statistic(
            @Parameter(hidden = true) Principal principal
    ) {
        String currentPrincipalName = principal.getName();
        return userService.getStatisticResponse(currentPrincipalName);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/all")
    @Operation(summary = "Get the statistics of all users.")
    @ApiResponse(responseCode = "200", description = "The statistics of all users.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatisticResponse.class)))
    })
    public Set<StatisticResponse> getAllUsers() {
        return StatisticService.getAllUsersStatistics();
    }

    @GetMapping("/leaderboard")
    @Operation(summary = "Get the leaderboard of users.")
    @ApiResponse(responseCode = "200", description = "The leaderboard of users.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatisticResponse.class)))
    })
    public List<StatisticResponse> getLeaderboard() {
        return StatisticService.getLeaderboard();
    }

}
