package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.Role;
import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.StatisticResponse;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;

    public User loadUserByEmail(String email) {
        log.debug("Loading user by email: {}", email);

        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
    }

    public User loadUserByGivenAndFamilyName(String givenName, String familyName) {
        return userRepository.findByGivenNameAndFamilyName(givenName, familyName).orElseThrow(
                () -> new UsernameNotFoundException("User " + givenName + " " + familyName + " not found.")
        );
    }

    public Set<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles()))
                .collect(Collectors.toSet());
    }

    public UserResponse getUserResponse(String email) {
        User user = loadUserByEmail(email);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    public UserResponse updateUser(String email, Optional<String> givenName, Optional<String> familyName, Optional<String> userName, Optional<String> picture, Optional<Set<Role>> roles) {
        User user = loadUserByEmail(email);

        // Store the original state for logging, if necessary
        User originalUser = new User(user);

        // Update user properties only if they are present
        givenName.ifPresent(user::setGivenName);
        familyName.ifPresent(user::setFamilyName);
        userName.ifPresent(user::setUserName);
        picture.ifPresent(user::setPicture);
        roles.ifPresent(user::setRoles);

        // Save and log the update
        userRepository.save(user);
        log.info("User updated from: {} to: {}", originalUser, user);

        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    public void deleteUser(String email) {
        User user = loadUserByEmail(email);
        userRepository.delete(user);
    }

    public UserResponse banUser(String email) {
        User user = loadUserByEmail(email);
        Set<Role> roles = user.getRoles();
        roles.add(Role.BANNED);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    public UserResponse unbanUser(String email) {
        User user = loadUserByEmail(email);
        Set<Role> roles = user.getRoles();
        roles.remove(Role.BANNED);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    public StatisticResponse getStatisticResponse(String email) {
        User user = loadUserByEmail(email);
        return new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage());
    }
}
