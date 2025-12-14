package com.fitplanhub.controller;

import com.fitplanhub.dto.ApiResponse;
import com.fitplanhub.dto.UserSignupRequest;
import com.fitplanhub.dto.TrainerSignupRequest;
import com.fitplanhub.dto.LoginRequest;
import com.fitplanhub.dto.LoginResponse;
import com.fitplanhub.entity.User;
import com.fitplanhub.entity.Trainer;
import com.fitplanhub.service.UserService;
import com.fitplanhub.service.TrainerService;
import com.fitplanhub.service.UserAuthService;
import com.fitplanhub.service.TrainerAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Authentication Controller
 * Handles user and trainer signup and login API endpoints
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React frontend
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private TrainerAuthService trainerAuthService;

    /**
     * User Signup Endpoint
     * POST /api/auth/signup/user
     * 
     * Example Request Body:
     * {
     *   "fullName": "John Doe",
     *   "email": "john@example.com",
     *   "password": "password123",
     *   "age": 25,
     *   "gender": "male",
     *   "height": 175.5,
     *   "weight": 70.0,
     *   "fitnessGoal": "weight_loss"
     * }
     * 
     * Example Success Response:
     * {
     *   "success": true,
     *   "message": "User registered successfully",
     *   "data": {
     *     "userId": 1,
     *     "email": "john@example.com",
     *     "fullName": "John Doe"
     *   }
     * }
     * 
     * @param signupRequest the user signup data
     * @param bindingResult validation results
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/signup/user")
    public ResponseEntity<?> signupUser(
            @Valid @RequestBody UserSignupRequest signupRequest,
            BindingResult bindingResult) {
        
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                return ResponseEntity
                        .badRequest()
                        .body(ApiResponse.error("Validation failed: " + errors));
            }

            // Register the user
            User user = userService.registerUser(signupRequest);

            // Prepare response data (exclude sensitive information like password)
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("userId", user.getId());
            responseData.put("email", user.getEmail());
            responseData.put("fullName", user.getFullName());
            responseData.put("fitnessGoal", user.getFitnessGoal());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("User registered successfully", responseData));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred during registration"));
        }
    }

    /**
     * Trainer Signup Endpoint
     * POST /api/auth/signup/trainer
     * 
     * Example Request Body:
     * {
     *   "fullName": "Jane Smith",
     *   "email": "jane@example.com",
     *   "password": "password123",
     *   "yearsOfExperience": 5,
     *   "specializations": "Weight Loss, Strength Training, Yoga",
     *   "bio": "Certified fitness trainer with 5 years of experience..."
     * }
     * 
     * Example Success Response:
     * {
     *   "success": true,
     *   "message": "Trainer registered successfully",
     *   "data": {
     *     "trainerId": 1,
     *     "email": "jane@example.com",
     *     "fullName": "Jane Smith",
     *     "specializations": "Weight Loss, Strength Training, Yoga"
     *   }
     * }
     * 
     * @param signupRequest the trainer signup data
     * @param bindingResult validation results
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/signup/trainer")
    public ResponseEntity<?> signupTrainer(
            @Valid @RequestBody TrainerSignupRequest signupRequest,
            BindingResult bindingResult) {
        
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                return ResponseEntity
                        .badRequest()
                        .body(ApiResponse.error("Validation failed: " + errors));
            }

            // Register the trainer
            Trainer trainer = trainerService.registerTrainer(signupRequest);

            // Prepare response data (exclude sensitive information like password)
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("trainerId", trainer.getId());
            responseData.put("email", trainer.getEmail());
            responseData.put("fullName", trainer.getFullName());
            responseData.put("specializations", trainer.getSpecializations());
            responseData.put("yearsOfExperience", trainer.getYearsOfExperience());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Trainer registered successfully", responseData));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred during registration"));
        }
    }

    /**
     * User Login Endpoint
     * POST /api/auth/login/user
     * 
     * Example Request Body:
     * {
     *   "email": "john@example.com",
     *   "password": "password123"
     * }
     * 
     * Example Success Response:
     * {
     *   "token": "eyJhbGciOiJIUzI1NiJ9...",
     *   "id": 1,
     *   "email": "john@example.com",
     *   "role": "USER",
     *   "fullName": "John Doe"
     * }
     * 
     * @param loginRequest contains email and password
     * @param bindingResult validation results
     * @return ResponseEntity with JWT token or error message
     */
    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult) {
        
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                return ResponseEntity
                        .badRequest()
                        .body(ApiResponse.error("Validation failed: " + errors));
            }

            // Authenticate user and generate token
            LoginResponse loginResponse = userAuthService.loginUser(loginRequest);

            return ResponseEntity.ok(loginResponse);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred during login"));
        }
    }

    /**
     * Trainer Login Endpoint
     * POST /api/auth/login/trainer
     * 
     * Example Request Body:
     * {
     *   "email": "jane@example.com",
     *   "password": "password123"
     * }
     * 
     * Example Success Response:
     * {
     *   "token": "eyJhbGciOiJIUzI1NiJ9...",
     *   "id": 1,
     *   "email": "jane@example.com",
     *   "role": "TRAINER",
     *   "fullName": "Jane Smith"
     * }
     * 
     * @param loginRequest contains email and password
     * @param bindingResult validation results
     * @return ResponseEntity with JWT token or error message
     */
    @PostMapping("/login/trainer")
    public ResponseEntity<?> loginTrainer(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult) {
        
        try {
            // Check for validation errors
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(", "));
                return ResponseEntity
                        .badRequest()
                        .body(ApiResponse.error("Validation failed: " + errors));
            }

            // Authenticate trainer and generate token
            LoginResponse loginResponse = trainerAuthService.loginTrainer(loginRequest);

            return ResponseEntity.ok(loginResponse);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An error occurred during login"));
        }
    }
}
