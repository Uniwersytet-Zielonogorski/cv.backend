package dev.chatverse.backend.documents;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Input {

    private String message;

    public Input() {
    }

    public Input(String name) {
        this.message = name;
    }

}