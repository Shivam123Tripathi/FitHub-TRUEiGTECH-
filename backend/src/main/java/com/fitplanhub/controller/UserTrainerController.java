package com.fitplanhub.controller;

import com.fitplanhub.dto.TrainerResponse;
import com.fitplanhub.service.FollowService;
import com.fitplanhub.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User Trainer Controller
 * Handles user endpoints for viewing and following trainers
 * Requires USER role (enforced by SecurityConfig)
 */
@RestController
@RequestMapping("/api/user/trainers")
@CrossOrigin(origins = "http://localhost:3000")
public class UserTrainerController {

    @Autowired
    private TrainerService trainerService;
    
    @Autowired
    private FollowService followService;

    /**
     * Get all trainers
     * GET /api/user/trainers
     */
    @GetMapping
    public ResponseEntity<?> getAllTrainers(@RequestAttribute("userId") Long userId) {
        try {
            List<TrainerResponse> trainers = trainerService.getAllTrainers(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", trainers);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch trainers: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Search trainers by keyword
     * GET /api/user/trainers/search?keyword=cardio
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchTrainers(@RequestAttribute("userId") Long userId,
                                            @RequestParam String keyword) {
        try {
            List<TrainerResponse> trainers = trainerService.searchTrainers(keyword, userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", trainers);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to search trainers: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Follow a trainer
     * POST /api/user/trainers/follow/{trainerId}
     */
    @PostMapping("/follow/{trainerId}")
    public ResponseEntity<?> followTrainer(@RequestAttribute("userId") Long userId,
                                           @PathVariable Long trainerId) {
        try {
            followService.followTrainer(userId, trainerId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully followed trainer");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to follow trainer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Unfollow a trainer
     * DELETE /api/user/trainers/follow/{trainerId}
     */
    @DeleteMapping("/follow/{trainerId}")
    public ResponseEntity<?> unfollowTrainer(@RequestAttribute("userId") Long userId,
                                             @PathVariable Long trainerId) {
        try {
            followService.unfollowTrainer(userId, trainerId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully unfollowed trainer");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to unfollow trainer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
