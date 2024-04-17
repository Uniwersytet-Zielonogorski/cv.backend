package dev.chatverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@AllArgsConstructor
public class StatisticResponse {
        private final ObjectId id;
        private final Integer messageCount;
        private final List<String> toxicMessages;
        private final float toxicPercentage;
}

