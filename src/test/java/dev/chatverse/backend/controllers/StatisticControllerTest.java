package dev.chatverse.backend.controllers;

import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.services.StatisticService;
import dev.chatverse.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatisticControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private StatisticService statisticService;

    private StatisticController statisticController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statisticController = new StatisticController(userService, statisticService);
    }

    @Test
    void testGetStatisticResponse() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        StatisticResponse statisticResponse = new StatisticResponse("1", 100, List.of("Toxic message"), 50.0f);
        when(userService.getStatisticResponse(principal.getName())).thenReturn(statisticResponse);

        StatisticResponse result = statisticController.statistic(principal);

        assertEquals(statisticResponse, result);
        verify(userService).getStatisticResponse(principal.getName());
    }

    @Test
    void testGetAllUsersStatistics() {
        Set<StatisticResponse> statistics = new HashSet<>();
        when(statisticService.getAllUsersStatistics()).thenReturn(statistics);

        Set<StatisticResponse> result = statisticController.getAllUsers();

        assertEquals(statistics, result);
        verify(statisticService).getAllUsersStatistics();
    }

    @Test
    void testGetLeaderboard() {
        List<StatisticResponse> leaderboard = new ArrayList<>();
        when(statisticService.getLeaderboard()).thenReturn(leaderboard);

        List<StatisticResponse> result = statisticController.getLeaderboard();

        assertEquals(leaderboard, result);
        verify(statisticService).getLeaderboard();
    }
}
