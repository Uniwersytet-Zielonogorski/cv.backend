package dev.chatverse.backend.documents;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {

    private String senderEmail;
    private String message;

    public Response() {
    }

    public Response(String name, String senderEmail) {
        this.message = name;
        this.senderEmail = senderEmail;
    }

}