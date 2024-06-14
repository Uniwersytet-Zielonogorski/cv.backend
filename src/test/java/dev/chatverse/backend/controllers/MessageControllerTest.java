package dev.chatverse.backend.controllers;

import dev.chatverse.backend.documents.Input;
import dev.chatverse.backend.documents.Response;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    @Mock
    private MessageService messageService;

    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageController = new MessageController(messageService);
    }

    @Test
    void testMessage() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        Input inputMessage = new Input();
        inputMessage.setMessage("Hello");

        UserResponse userResponse = new UserResponse("1", "test@example.com", "Test User", "test.jpg", Set.of());
        Response response = new Response(userResponse, new Response.Toxicity(true), "Hello", new Date());

        when(messageService.handleReceivedMessage(principal.getName(), inputMessage.getMessage()))
                .thenReturn(response);

        Response result = messageController.message(principal, inputMessage);

        assertEquals(response, result);
        verify(messageService).handleReceivedMessage(principal.getName(), inputMessage.getMessage());
    }
}
