package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.Response;
import dev.chatverse.backend.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final UserService userService;

    public Response handleReceivedMessage(String senderEmail, String message) {
        UserResponse sender = userService.getUserResponse(senderEmail);

        return Response.builder()
                .sender(sender)
                .message(message)
                .build();
    }
}
