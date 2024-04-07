package dev.chatverse.backend.controllers;

import dev.chatverse.backend.documents.Input;
import dev.chatverse.backend.documents.Response;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Response greeting(Input message) throws Exception {
//        String email = principal.getName();
        Thread.sleep(1000); // simulated delay
        return new Response(HtmlUtils.htmlEscape(message.getMessage()), "bykowski@dev.pl");
    }
}

