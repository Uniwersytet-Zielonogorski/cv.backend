package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.Response;
import dev.chatverse.backend.dto.AnalysisResponse;
import dev.chatverse.backend.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@AllArgsConstructor
public class MessageService {

    private final UserService userService;
    private final PerspectiveService perspectiveService;

    /**
     * Handle a received message.
     *
     * @param senderEmail the email of the sender
     * @param message     the message received
     * @return the response to the received message
     */
    public Response handleReceivedMessage(String senderEmail, String message) {
        UserResponse sender = userService.getUserResponse(senderEmail);

        AnalysisResponse analysisResponse = perspectiveService.analyzeComment(message).block();
        Boolean isToxic = analysisResponse.isToxic();

        userService.incrementMessageCount(senderEmail);

        if (isToxic) {
            sender = userService.addToxicMessage(senderEmail, message);
        }

        return Response.builder()
                .sender(sender)
                .message(message)
                .timestamp(new Date())
                .toxicity(new Response.Toxicity(isToxic))
                .build();
    }
}
