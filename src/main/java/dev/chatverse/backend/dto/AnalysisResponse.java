package dev.chatverse.backend.dto;

import lombok.Data;

import java.util.List;

import java.util.Map;

@Data
public class AnalysisResponse {
    private Map<String, ScoreDetail> attributeScores;
    private List<String> languages;
    private List<String> detectedLanguages;
    private String clientToken;

    @Data
    public static class ScoreDetail {
        private Score summaryScore;
        private List<SpanScore> spanScores;

        @Data
        public static class SpanScore {
            private int begin;
            private int end;
            private Score score;
        }

        @Data
        public static class Score {
            private float value;
            private String type;
        }
    }

    public Boolean isToxic() {
        if(attributeScores == null || attributeScores.get("TOXICITY") == null || attributeScores.get("TOXICITY").getSummaryScore() == null)
            return false;
        return attributeScores.get("TOXICITY").getSummaryScore().getValue() > 0.7;
    }
}


