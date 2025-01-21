package com.example.um.Auth;

import com.example.um.User.User;
import com.example.um.User.UserRepository;
import com.example.um.utils.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String email, String rawPassword, User.Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        String hashedPassword = PasswordUtil.hashPassword(rawPassword);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);

        return userRepository.save(user);
    }

    public User authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!PasswordUtil.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return user;
    }
}
