package edu.escuelaing.arep.controller;

import edu.escuelaing.arep.model.User;
import edu.escuelaing.arep.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserLoginControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserLoginController userLoginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() throws Exception {
        String username = "testuser";
        String password = "password123";

        // Simular el retorno de un usuario cuando se autentica correctamente
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userService.authenticateUser(username, password)).thenReturn(user);

        ResponseEntity<String> response = userLoginController.login(username, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("autenticado", response.getBody());
        verify(userService, times(1)).authenticateUser(username, password);
    }

    @Test
    void login_Failure() throws Exception {
        String username = "testuser";
        String password = "wrongpassword";

        // Simular la excepción en el método authenticateUser
        when(userService.authenticateUser(username, password)).thenThrow(new RuntimeException("Invalid password"));

        ResponseEntity<String> response = userLoginController.login(username, password);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Invalid password", response.getBody());
        verify(userService, times(1)).authenticateUser(username, password);
    }

    @Test
    void register_Success() throws Exception {
        String username = "newuser";
        String password = "password123";

        // Simular el retorno de un nuevo usuario registrado
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userService.registerNewUser(username, password)).thenReturn(user);

        ResponseEntity<String> response = userLoginController.register(username, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Creado", response.getBody());
        verify(userService, times(1)).registerNewUser(username, password);
    }

    @Test
    void register_Failure_UsernameAlreadyExists() throws Exception {
        String username = "existinguser";
        String password = "password123";

        // Simular la excepción cuando el nombre de usuario ya existe
        when(userService.registerNewUser(username, password)).thenThrow(new RuntimeException("Username already exists"));

        ResponseEntity<String> response = userLoginController.register(username, password);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Username already exists", response.getBody());
        verify(userService, times(1)).registerNewUser(username, password);
    }
}
