package edu.escuelaing.arep.service;

import edu.escuelaing.arep.model.User;
import edu.escuelaing.arep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(String username, String password) throws NoSuchAlgorithmException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        user.setUsername(username);
        user.setPassword(hashPassword(password));
        return userRepository.save(user);
    }

    public User authenticateUser(String username, String password) throws NoSuchAlgorithmException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!hashPassword(password).equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return user;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
