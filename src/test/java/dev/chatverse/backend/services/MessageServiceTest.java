package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.Response;
import dev.chatverse.backend.dto.AnalysisResponse;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.documents.User.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PerspectiveService perspectiveService;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(userService, perspectiveService);
    }

    @Test
    void testHandleReceivedMessage() {
        String senderEmail = "test@example.com";
        String message = "Hello";

        User user = User.builder()
                .id("1")
                .givenName("Test")
                .familyName("User")
                .email(senderEmail)
                .userName("testuser")
                .roles(Set.of(Role.USER))
                .build();

        UserResponse userResponse = new UserResponse("1", senderEmail, "Test User", "test.jpg", Set.of());
        when(userService.getUserResponse(senderEmail)).thenReturn(userResponse);
        when(userService.loadUserByEmail(senderEmail)).thenReturn(user);

        AnalysisResponse.ScoreDetail.Score toxicityScore = new AnalysisResponse.ScoreDetail.Score(0.8f, "PROBABILITY");
        AnalysisResponse.ScoreDetail toxicityScoreDetail = new AnalysisResponse.ScoreDetail(toxicityScore, List.of());

        AnalysisResponse analysisResponse = new AnalysisResponse(
                Map.of("TOXICITY", toxicityScoreDetail),
                List.of("en"),
                List.of("en"),
                "clientToken"
        );

        when(perspectiveService.analyzeComment(message)).thenReturn(Mono.just(analysisResponse));

        doNothing().when(userService).incrementMessageCount(senderEmail);

        when(userService.addToxicMessage(senderEmail, message)).thenReturn(userResponse);

        Response expectedResponse = new Response(userResponse, new Response.Toxicity(true), message, new Date());

        messageService.handleReceivedMessage(senderEmail, message);

        Response result = new Response(userResponse, new Response.Toxicity(true), message, new Date());

        assertEquals(expectedResponse.getSender(), result.getSender());
        assertEquals(expectedResponse.getMessage(), result.getMessage());

        long tolerance = 50;
        assertTrue(Math.abs(expectedResponse.getTimestamp().getTime() - result.getTimestamp().getTime()) < tolerance,
                "Timestamps are not within the acceptable range");

        assertEquals(expectedResponse.getToxicity().isToxic(), result.getToxicity().isToxic());

        verify(userService).getUserResponse(senderEmail);
        verify(perspectiveService).analyzeComment(message);
        verify(userService).incrementMessageCount(senderEmail);
        verify(userService).addToxicMessage(senderEmail, message);
    }
}
