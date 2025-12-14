package com.fitplanhub.service;

import com.fitplanhub.dto.LoginRequest;
import com.fitplanhub.dto.LoginResponse;
import com.fitplanhub.entity.User;
import com.fitplanhub.repository.UserRepository;
import com.fitplanhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication Service for Users
 * Handles user login and authentication
 */
@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticate user and generate JWT token
     * 
     * @param loginRequest contains email and password
     * @return LoginResponse with JWT token and user info
     * @throws RuntimeException if authentication fails
     */
    public LoginResponse loginUser(LoginRequest loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password using BCrypt
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        // Return login response with token and user details
        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getFullName()
        );
    }
}
