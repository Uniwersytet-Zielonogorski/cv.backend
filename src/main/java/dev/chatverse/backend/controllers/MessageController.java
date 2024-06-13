package dev.chatverse.backend.controllers;

import dev.chatverse.backend.documents.Input;
import dev.chatverse.backend.documents.Response;
import dev.chatverse.backend.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    @Operation(summary = "Send a message.")
    @ApiResponse(responseCode = "200", description = "The message has been successfully sent.")
    public Response message(Principal principal, Input message) {
        return messageService.handleReceivedMessage(principal.getName(), message.getMessage());
    }
}

