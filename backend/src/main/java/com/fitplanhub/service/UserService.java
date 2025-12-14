package com.fitplanhub.service;

import com.fitplanhub.dto.UserSignupRequest;
import com.fitplanhub.entity.User;
import com.fitplanhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service
 * Handles business logic for user operations
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     * Validates email uniqueness, hashes password, and saves user to database
     * 
     * @param signupRequest the user signup data
     * @return the saved user entity
     * @throws RuntimeException if email already exists or other errors occur
     */
    @Transactional
    public User registerUser(UserSignupRequest signupRequest) {
        // Check if email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user entity
        User user = new User();
        user.setFullName(signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        
        // Hash the password using BCrypt before saving
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        
        user.setAge(signupRequest.getAge());
        user.setGender(signupRequest.getGender());
        user.setHeight(signupRequest.getHeight());
        user.setWeight(signupRequest.getWeight());
        user.setFitnessGoal(signupRequest.getFitnessGoal());

        // Save user to database
        return userRepository.save(user);
    }
}
