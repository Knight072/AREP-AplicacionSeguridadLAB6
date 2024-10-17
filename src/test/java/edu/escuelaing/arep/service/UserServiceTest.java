package edu.escuelaing.arep.service;

import edu.escuelaing.arep.model.User;
import edu.escuelaing.arep.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerNewUser_Success() throws NoSuchAlgorithmException {
        String username = "testuser";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        User user = new User();
        user.setUsername(username);
        user.setPassword(userService.hashPassword(password));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerNewUser(username, password);

        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerNewUser_Failure_UsernameAlreadyExists() {
        String username = "existinguser";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerNewUser(username, password);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void authenticateUser_Success() throws NoSuchAlgorithmException {
        String username = "testuser";
        String password = "password123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(userService.hashPassword(password));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User authenticatedUser = userService.authenticateUser(username, password);

        assertNotNull(authenticatedUser);
        assertEquals(username, authenticatedUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void authenticateUser_Failure_UserNotFound() {
        String username = "nonexistentuser";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticateUser(username, password);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void authenticateUser_Failure_InvalidPassword() throws NoSuchAlgorithmException {
        String username = "testuser";
        String password = "password123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(userService.hashPassword("wrongpassword"));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticateUser(username, password);
        });

        assertEquals("Invalid password", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}

