package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByEmailSuccess() {
        User user = User.builder().givenName("John").familyName("Doe").email("test@example.com").locale("en-US").picture("http://example.com/picture").roles(new HashSet<>()).userName("john.doe").build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User result = userService.loadUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("John", result.getGivenName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("john.doe", result.getUserName());
        assertEquals("http://example.com/picture", result.getPicture());
        assertEquals(new HashSet<>(), result.getToxicMessages());

    }

    @Test
    void testLoadUserByEmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByEmail("nonexistent@example.com"));
    }

    @Test
    void testGetUserResponse() {
        User user = User.builder().givenName("John").familyName("Doe").email("test@example.com").locale("en-US").picture("http://example.com/picture").roles(new HashSet<>()).userName("john.doe").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserResponse userResponse = userService.getUserResponse("test@example.com");

        assertNotNull(userResponse);
        assertEquals("1", userResponse.getId());
        assertEquals("test@example.com", userResponse.getEmail());
        assertEquals("john.doe", userResponse.getUserName());
        assertEquals("http://example.com/picture", userResponse.getPictureUrl());
    }
}
