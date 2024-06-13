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


    /**
     * Analyze the given comment for toxicity and profanity.
     *
     * @param comment the comment to analyze
     * @return the analysis response of the comment
     */
    public Mono<AnalysisResponse> analyzeComment(String comment) {
        String requestBody = buildRequestBody(comment, List.of("TOXICITY", "PROFANITY"));

        return webClient.post()
                .uri("/comments:analyze?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve().bodyToMono(AnalysisResponse.class);
    }

    /**
     * Build the request body for the Perspective API.
     *
     * @param comment    the comment to analyze
     * @param attributes the attributes to request
     * @return the request body for the Perspective API
     */
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
