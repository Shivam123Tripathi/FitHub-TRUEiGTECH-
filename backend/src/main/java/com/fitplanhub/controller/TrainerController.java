package com.fitplanhub.controller;

import com.fitplanhub.dto.FitnessPlanRequest;
import com.fitplanhub.dto.FitnessPlanResponse;
import com.fitplanhub.service.TrainerPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Trainer Controller
 * Handles trainer-specific endpoints for managing fitness plans
 * Requires TRAINER role (enforced by SecurityConfig)
 */
@RestController
@RequestMapping("/api/trainer")
@CrossOrigin(origins = "http://localhost:3000")
public class TrainerController {

    @Autowired
    private TrainerPlanService trainerPlanService;

    /**
     * Create a new fitness plan
     * POST /api/trainer/plans
     */
    @PostMapping("/plans")
    public ResponseEntity<?> createPlan(@RequestAttribute("userId") Long trainerId,
                                        @Valid @RequestBody FitnessPlanRequest request) {
        try {
            FitnessPlanResponse plan = trainerPlanService.createPlan(trainerId, request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fitness plan created successfully");
            response.put("data", plan);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get all plans created by the trainer
     * GET /api/trainer/plans
     */
    @GetMapping("/plans")
    public ResponseEntity<?> getTrainerPlans(@RequestAttribute("userId") Long trainerId) {
        try {
            List<FitnessPlanResponse> plans = trainerPlanService.getTrainerPlans(trainerId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", plans);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Update a fitness plan
     * PUT /api/trainer/plans/{planId}
     */
    @PutMapping("/plans/{planId}")
    public ResponseEntity<?> updatePlan(@RequestAttribute("userId") Long trainerId,
                                        @PathVariable Long planId,
                                        @Valid @RequestBody FitnessPlanRequest request) {
        try {
            FitnessPlanResponse plan = trainerPlanService.updatePlan(trainerId, planId, request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fitness plan updated successfully");
            response.put("data", plan);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Delete a fitness plan
     * DELETE /api/trainer/plans/{planId}
     */
    @DeleteMapping("/plans/{planId}")
    public ResponseEntity<?> deletePlan(@RequestAttribute("userId") Long trainerId,
                                        @PathVariable Long planId) {
        try {
            trainerPlanService.deletePlan(trainerId, planId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Fitness plan deleted successfully");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
