package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
    }

    public User loadUserByGivenAndFamilyName(String givenName, String familyName) {
        return userRepository.findByGivenNameAndFamilyName(givenName, familyName).orElseThrow(
                () -> new UsernameNotFoundException("User " + givenName + " " + familyName + " not found.")
        );
    }

    public UserResponse getUserResponse(String email) {
        User user = loadUserByEmail(email);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture());
    }

    public StatisticResponse getStatisticResponse(String email) {
        User user = loadUserByEmail(email);
        return new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage());
    }

    public String getUserPicture(String email) {
        User user = loadUserByEmail(email);
        String pictureUrl = user.getPicture();
        System.out.println(pictureUrl);
        String familyName = user.getFamilyName();

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <img src=\"" + pictureUrl + "\" alt=\"User Picture\"/>\n" +
                "    <h1>" + familyName + "</h1>\n" +
                "</body>\n" +

                "</html>";
    }
}
