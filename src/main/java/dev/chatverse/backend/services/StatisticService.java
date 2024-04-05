package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticService {
    UserRepository userRepository;
    private User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
    }


    public StatisticResponse getStatisticResponse(String email) {
        User user = loadUserByEmail(email);
        return new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage());
    }

    public Set<StatisticResponse> getAllUsersStatistic() {
        return userRepository.findAll().stream()
                .map(user -> new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage()))
                .collect(Collectors.toSet());
    }

}
