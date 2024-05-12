package dev.chatverse.backend.dto;

import lombok.Getter;

import java.util.List;

@Getter
public record StatisticResponse(String id, Integer messageCount, List<String> toxicMessages, float toxicPercentage) {
}

