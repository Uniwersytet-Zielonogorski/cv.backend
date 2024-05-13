package dev.chatverse.backend.services;

import dev.chatverse.backend.dto.AnalysisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

@Service
public class PerspectiveService {

    @Autowired
    private WebClient webClient;

    @Value("${perspective.api.key}")
    private String apiKey;


    public Mono<AnalysisResponse> analyzeComment(String comment) {
        String requestBody = buildRequestBody(comment, List.of("TOXICITY", "PROFANITY"));

        return webClient.post()
                .uri("/comments:analyze?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve().bodyToMono(AnalysisResponse.class);
    }

    private String buildRequestBody(String comment, List<String> attributes) {
        StringBuilder attributesJson = new StringBuilder();
        for (String attribute : attributes) {
            attributesJson.append(String.format("\"%s\": {},", attribute));
        }

        // Remove the last comma to prevent JSON syntax error
        if (!attributesJson.isEmpty()) {
            attributesJson.setLength(attributesJson.length() - 1);
        }

        return String.format(
                "{\"comment\": {\"text\": \"%s\"},"
                        + "\"languages\": [\"en\"],"
                        + "\"requestedAttributes\": {%s}"
                        + "}", comment, attributesJson);
    }
}
