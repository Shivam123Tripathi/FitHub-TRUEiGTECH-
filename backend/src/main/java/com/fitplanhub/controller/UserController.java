package com.fitplanhub.controller;

import com.fitplanhub.dto.FitnessPlanResponse;
import com.fitplanhub.service.FollowService;
import com.fitplanhub.service.SubscriptionService;
import com.fitplanhub.service.UserFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User Controller
 * Handles user-specific endpoints for subscriptions, following trainers, and feed
 * Requires USER role (enforced by SecurityConfig)
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserFeedService userFeedService;

    /**
     * Subscribe to a fitness plan
     * POST /api/user/subscribe/{planId}
     */
    @PostMapping("/subscribe/{planId}")
    public ResponseEntity<?> subscribeToPlan(@RequestAttribute("userId") Long userId,
                                             @PathVariable Long planId) {
        try {
            subscriptionService.subscribeToPlan(userId, planId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully subscribed to plan");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Follow a trainer
     * POST /api/user/follow/{trainerId}
     */
    @PostMapping("/follow/{trainerId}")
    public ResponseEntity<?> followTrainer(@RequestAttribute("userId") Long userId,
                                           @PathVariable Long trainerId) {
        try {
            followService.followTrainer(userId, trainerId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully followed trainer");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Unfollow a trainer
     * DELETE /api/user/unfollow/{trainerId}
     */
    @DeleteMapping("/unfollow/{trainerId}")
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
        }
    }

    /**
     * Get list of followed trainer IDs
     * GET /api/user/following
     */
    @GetMapping("/following")
    public ResponseEntity<?> getFollowedTrainers(@RequestAttribute("userId") Long userId) {
        try {
            List<Long> followedTrainerIds = followService.getFollowedTrainerIds(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", followedTrainerIds);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get list of subscribed plan IDs
     * GET /api/user/subscriptions
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<?> getSubscribedPlans(@RequestAttribute("userId") Long userId) {
        try {
            List<Long> subscribedPlanIds = subscriptionService.getSubscribedPlanIds(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", subscribedPlanIds);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get personalized user feed (plans from followed trainers)
     * GET /api/user/feed
     */
    @GetMapping("/feed")
    public ResponseEntity<?> getUserFeed(@RequestAttribute("userId") Long userId) {
        try {
            List<FitnessPlanResponse> feedPlans = userFeedService.getUserFeed(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", feedPlans);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get all available plans (for browsing)
     * GET /api/user/plans
     */
    @GetMapping("/plans")
    public ResponseEntity<?> getAllPlans() {
        try {
            List<FitnessPlanResponse> allPlans = userFeedService.getAllPlans();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", allPlans);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
