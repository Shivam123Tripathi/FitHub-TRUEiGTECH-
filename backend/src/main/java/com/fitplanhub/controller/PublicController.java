package com.fitplanhub.controller;

import com.fitplanhub.dto.FitnessPlanResponse;
import com.fitplanhub.service.UserFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Public endpoints - no auth needed
 * Mainly used for landing page to show featured content
 */
@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:3000")
public class PublicController {

    @Autowired
    private UserFeedService userFeedService;

    // Landing page needs to show some plans without requiring login
    @GetMapping("/plans")
    public ResponseEntity<?> getPopularPlans(@RequestParam(defaultValue = "6") int limit) {
        try {
            List<FitnessPlanResponse> allPlans = userFeedService.getAllPlans();
            
            // just show first N plans, keeps response light
            List<FitnessPlanResponse> featured = allPlans.stream()
                .limit(limit)
                .collect(Collectors.toList());
            
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);
            resp.put("data", featured);
            
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            Map<String, Object> errorResp = new HashMap<>();
            errorResp.put("success", false);
            errorResp.put("message", "Failed to fetch plans");
            return ResponseEntity.status(500).body(errorResp);
        }
    }
}
