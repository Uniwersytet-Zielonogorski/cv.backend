package dev.chatverse.backend.controllers;

import dev.chatverse.backend.Forms.EditUserForm;
import dev.chatverse.backend.dto.UserResponse;
import dev.chatverse.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void testMe() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        UserResponse userResponse = new UserResponse("1", "test@example.com", "Test User", "test.jpg", Set.of());
        when(userService.getUserResponse(principal.getName())).thenReturn(userResponse);

        UserResponse result = userController.me(principal);

        assertEquals(userResponse, result);
        verify(userService).getUserResponse(principal.getName());
    }

    @Test
    void testUpdateMe() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        EditUserForm editUserForm = new EditUserForm();
        editUserForm.setGivenName("Updated Name");

        UserResponse userResponse = new UserResponse("1", "test@example.com", "Updated User", "test.jpg", Set.of());
        when(userService.updateUser(
                eq(principal.getName()),
                eq(editUserForm.getGivenName()),
                eq(editUserForm.getFamilyName()),
                eq(editUserForm.getUserName()),
                eq(editUserForm.getPicture()),
                any())).thenReturn(userResponse);

        UserResponse result = userController.updateMe(editUserForm, principal);

        assertEquals(userResponse, result);
        verify(userService).updateUser(
                eq(principal.getName()),
                eq(editUserForm.getGivenName()),
                eq(editUserForm.getFamilyName()),
                eq(editUserForm.getUserName()),
                eq(editUserForm.getPicture()),
                any());
    }

    @Test
    void testDeleteMe() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        doNothing().when(userService).deleteUser(anyString());

        userController.deleteMe(principal);

        verify(userService).deleteUser(principal.getName());
    }

    @Test
    void testGetAllUsers() {
        Set<UserResponse> users = Set.of(
                new UserResponse("1", "test1@example.com", "Test User 1", "test1.jpg", Set.of()),
                new UserResponse("2", "test2@example.com", "Test User 2", "test2.jpg", Set.of())
        );
        when(userService.getAllUsers()).thenReturn(users);

        Set<UserResponse> result = userController.getAllUsers();

        assertEquals(users, result);
        verify(userService).getAllUsers();
    }
}
