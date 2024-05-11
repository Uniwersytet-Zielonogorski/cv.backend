package dev.chatverse.backend.controllers;

import dev.chatverse.backend.documents.Input;
import dev.chatverse.backend.documents.Response;
import dev.chatverse.backend.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Response greeting(Principal principal, Input message) {
        return messageService.handleReceivedMessage(principal.getName(), message.getMessage());
    }
}

