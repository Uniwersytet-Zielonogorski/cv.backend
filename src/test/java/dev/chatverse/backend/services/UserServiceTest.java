package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.Role;
import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testLoadUserByEmailSuccess() {
        String email = "test@example.com";
        User user = User.builder()
                .id("1")
                .givenName("Test")
                .familyName("User")
                .email(email)
                .userName("testuser")
                .roles(Set.of(Role.USER))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.loadUserByEmail(email);

        assertEquals(user, result);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testGetUserResponse() {
        String email = "test@example.com";
        User user = User.builder()
                .id("1")
                .givenName("Test")
                .familyName("User")
                .email(email)
                .userName("testuser")
                .roles(Set.of(Role.USER))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserResponse result = userService.getUserResponse(email);

        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
    }
}
