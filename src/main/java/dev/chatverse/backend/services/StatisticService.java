package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticService {
    UserRepository userRepository;
    UserService userService;

    /**
     * Get the statistic of the user with the given email.
     *
     * @param email the email of the user to get the statistic of
     * @return the statistic of the user with the given email
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    public StatisticResponse getStatisticResponse(String email) {
        User user = userService.loadUserByEmail(email);
        return new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage());
    }

    /**
     * Get the statistics of all users.
     *
     * @return a set of all users' statistics
     */
    public Set<StatisticResponse> getAllUsersStatistics() {
        return userRepository.findAll().stream()
                .map(user -> new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage()))
                .collect(Collectors.toSet());
    }

    /**
     * Get the leaderboard of users.
     *
     * @return a list of the top 10 users' statistics
     */
    public List<StatisticResponse> getLeaderboard() {
        return userRepository.findAll().stream()
                .map(user -> new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage()))
                .sorted((o1, o2) -> (int) (o2.toxicPercentage() - o1.toxicPercentage()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
