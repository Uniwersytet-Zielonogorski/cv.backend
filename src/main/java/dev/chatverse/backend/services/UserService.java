package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.User;
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

    public UserResponse getUserResponse(String id) {
        User user = loadUserByEmail(id);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getToxicMessages());
    }
}
