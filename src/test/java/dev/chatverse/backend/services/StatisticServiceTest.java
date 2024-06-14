package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatisticServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    private StatisticService statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statisticService = new StatisticService(userRepository, userService);
    }

    @Test
    void testGetStatisticResponse() {
        String email = "test@example.com";
        User user = new User();
        user.setId("1");
        user.setMessageCount(100);
        user.setToxicMessages(List.of("Toxic message"));
        user.setToxicPercentage(50.0f);

        when(userService.loadUserByEmail(email)).thenReturn(user);

        StatisticResponse response = statisticService.getStatisticResponse(email);

        assertEquals("1", response.id());
        assertEquals(100, response.messageCount());
        assertEquals(List.of("Toxic message"), response.toxicMessages());
        assertEquals(50.0f, response.toxicPercentage());

        verify(userService).loadUserByEmail(email);
    }

    @Test
    void testGetAllUsersStatistics() {
        User user1 = new User();
        user1.setId("1");
        user1.setMessageCount(100);
        user1.setToxicMessages(List.of("Toxic message 1"));
        user1.setToxicPercentage(50.0f);

        User user2 = new User();
        user2.setId("2");
        user2.setMessageCount(200);
        user2.setToxicMessages(List.of("Toxic message 2"));
        user2.setToxicPercentage(75.0f);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        Set<StatisticResponse> responses = statisticService.getAllUsersStatistics();

        assertEquals(2, responses.size());
        assertEquals(Set.of(
                new StatisticResponse("1", 100, List.of("Toxic message 1"), 50.0f),
                new StatisticResponse("2", 200, List.of("Toxic message 2"), 75.0f)
        ), responses);

        verify(userRepository).findAll();
    }

    @Test
    void testGetLeaderboard() {
        User user1 = new User();
        user1.setId("1");
        user1.setMessageCount(100);
        user1.setToxicMessages(List.of("Toxic message 1"));
        user1.setToxicPercentage(50.0f);

        User user2 = new User();
        user2.setId("2");
        user2.setMessageCount(200);
        user2.setToxicMessages(List.of("Toxic message 2"));
        user2.setToxicPercentage(75.0f);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<StatisticResponse> leaderboard = statisticService.getLeaderboard();

        assertEquals(2, leaderboard.size());
        assertEquals(new StatisticResponse("2", 200, List.of("Toxic message 2"), 75.0f), leaderboard.get(0));
        assertEquals(new StatisticResponse("1", 100, List.of("Toxic message 1"), 50.0f), leaderboard.get(1));

        verify(userRepository).findAll();
    }
}
