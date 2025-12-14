package com.fitplanhub.controller;

import com.fitplanhub.service.PlanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Plan Controller
 * Handles plan details with access control based on subscription status
 */
@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = "http://localhost:3000")
public class PlanController {

    @Autowired
    private PlanDetailsService planDetailsService;

    /**
     * Get plan details
     * Returns full details if subscribed, preview otherwise
     * GET /api/plans/{planId}
     */
    @GetMapping("/{planId}")
    public ResponseEntity<?> getPlanDetails(@PathVariable Long planId,
                                            @RequestAttribute(value = "userId", required = false) Long userId,
                                            @RequestAttribute(value = "userRole", required = false) String userRole) {
        try {
            // If not authenticated, return preview only
            if (userId == null) {
                Object planData = planDetailsService.getPlanDetails(planId, null, null);
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("subscribed", false);
                response.put("data", planData);
                
                return ResponseEntity.ok(response);
            }

            // If authenticated, check subscription status
            Object planData = planDetailsService.getPlanDetails(planId, userId, userRole);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", planData);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(404).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "An error occurred");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
