package dev.chatverse.backend.documents;

import dev.chatverse.backend.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
public class Response {
    private UserResponse sender;
    private Toxicity toxicity;
    private String message;
    private Date timestamp;

    public record Toxicity(boolean isToxic) {
    }
}