package edu.escuelaing.arep.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserDefaultConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void testSetAndGetUsername() {
        // Arrange
        User user = new User();
        String username = "testUser";

        // Act
        user.setUsername(username);

        // Assert
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testSetAndGetPassword() {
        // Arrange
        User user = new User();
        String password = "testPassword";

        // Act
        user.setPassword(password);

        // Assert
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testSetAndGetId() {
        // Arrange
        User user = new User();
        Long id = 1L;

        // Act
        user.setId(id);

        // Assert
        assertEquals(id, user.getId());
    }
}
