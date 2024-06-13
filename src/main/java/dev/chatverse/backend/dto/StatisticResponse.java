package dev.chatverse.backend.dto;

import java.util.List;

public record StatisticResponse(
        String id,
        Integer messageCount,
        List<String> toxicMessages,
        float toxicPercentage
) {}

