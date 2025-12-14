package com.fitplanhub.service;

import com.fitplanhub.dto.LoginRequest;
import com.fitplanhub.dto.LoginResponse;
import com.fitplanhub.entity.Trainer;
import com.fitplanhub.repository.TrainerRepository;
import com.fitplanhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authentication Service for Trainers
 * Handles trainer login and authentication
 */
@Service
public class TrainerAuthService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticate trainer and generate JWT token
     * 
     * @param loginRequest contains email and password
     * @return LoginResponse with JWT token and trainer info
     * @throws RuntimeException if authentication fails
     */
    public LoginResponse loginTrainer(LoginRequest loginRequest) {
        // Find trainer by email
        Trainer trainer = trainerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify password using BCrypt
        if (!passwordEncoder.matches(loginRequest.getPassword(), trainer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(trainer.getId(), trainer.getEmail(), trainer.getRole());

        // Return login response with token and trainer details
        return new LoginResponse(
                token,
                trainer.getId(),
                trainer.getEmail(),
                trainer.getRole(),
                trainer.getFullName()
        );
    }
}
