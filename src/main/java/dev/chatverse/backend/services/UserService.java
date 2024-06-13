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

    /**
     * Load a user by email.
     *
     * @param email the email of the user to load
     * @return the user with the given email
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    public User loadUserByEmail(String email) {
        log.debug("Loading user by email: {}", email);

        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
    }

    /**
     * Load a user by given and family name.
     *
     * @param givenName  the given name of the user to load
     * @param familyName the family name of the user to load
     * @return the user with the given and family name
     * @throws UsernameNotFoundException if no user with the given and family name is found
     */
    public User loadUserByGivenAndFamilyName(String givenName, String familyName) {
        return userRepository.findByGivenNameAndFamilyName(givenName, familyName).orElseThrow(
                () -> new UsernameNotFoundException("User " + givenName + " " + familyName + " not found.")
        );
    }

    /**
     * Get all users.
     *
     * @return a set of all users' UserResponses
     */
    public Set<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles()))
                .collect(Collectors.toSet());
    }

    /**
     * Get a user by email.
     *
     * @param email the email of the user to get
     * @return the UserResponse of the user with the given email
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    public UserResponse getUserResponse(String email) {
        User user = loadUserByEmail(email);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    /**
     * Update a user.
     *
     * @param email      the email of the user to update
     * @param givenName  the new given name of the user
     * @param familyName the new family name of the user
     * @param userName   the new username of the user
     * @param picture    the new picture of the user
     * @param roles      the new roles of the user
     * @return the UserResponse of the updated user
     * @throws UsernameNotFoundException if no user with the given email is found
     */
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

    /**
     * Delete a user.
     *
     * @param email the email of the user to delete
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    public void deleteUser(String email) {
        User user = loadUserByEmail(email);
        userRepository.delete(user);
    }

    /**
     * Ban a user.
     *
     * @param email the email of the user to ban
     * @return the UserResponse of the banned user
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    public UserResponse banUser(String email) {
        User user = loadUserByEmail(email);
        Set<Role> roles = user.getRoles();
        roles.add(Role.BANNED);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    /**
     * Unban a user.
     *
     * @param email the email of the user to unban
     * @return the UserResponse of the unbanned user
     * @throws UsernameNotFoundException if no user with the given email is found
     */
    public UserResponse unbanUser(String email) {
        User user = loadUserByEmail(email);
        Set<Role> roles = user.getRoles();
        roles.remove(Role.BANNED);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    /**
     * Get all banned users.
     *
     * @return a set of all banned users' UserResponses
     * @throws UsernameNotFoundException if no banned users are found
     */
    public Set<UserResponse> getBannedUsers() {
        return userRepository.findByRoles(Set.of(Role.BANNED)).orElseThrow(
                        () -> new UsernameNotFoundException("No banned users found.")
                ).stream()
                .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles()))
                .collect(Collectors.toSet());
    }

    /**
     * Add a toxic message to a user.
     *
     * @param email   the email of the user to add the toxic message to
     * @param message the toxic message to add
     * @return the UserResponse of the user with the added toxic message
     */
    public UserResponse addToxicMessage(String email, String message) {
        User user = loadUserByEmail(email);
        user.getToxicMessages().add(message);
        user.setToxicPercentage((float) user.getToxicMessages().size() / user.getMessageCount() * 100);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    /**
     * Increment the message count of a user.
     *
     * @param email the email of the user to increment the message count of
     */
    public void incrementMessageCount(String email) {
        User user = loadUserByEmail(email);
        user.setMessageCount(user.getMessageCount() + 1);
        userRepository.save(user);
        new UserResponse(user.getId(), user.getEmail(), user.getUserName(), user.getPicture(), user.getRoles());
    }

    /**
     * Get the statistic response of a user.
     *
     * @param email the email of the user to get the statistic response of
     * @return the StatisticResponse of the user
     */
    public StatisticResponse getStatisticResponse(String email) {
        User user = loadUserByEmail(email);
        return new StatisticResponse(user.getId(), user.getMessageCount(), user.getToxicMessages(), user.getToxicPercentage());
    }

}
