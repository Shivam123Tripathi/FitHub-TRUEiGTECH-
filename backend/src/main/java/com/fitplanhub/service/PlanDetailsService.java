package com.fitplanhub.service;

import com.fitplanhub.dto.FitnessPlanPreview;
import com.fitplanhub.dto.FitnessPlanResponse;
import com.fitplanhub.entity.FitnessPlan;
import com.fitplanhub.repository.FitnessPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * Service for plan details with access control
 */
@Service
public class PlanDetailsService {

    @Autowired
    private FitnessPlanRepository fitnessPlanRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Get plan details with access control
     * Returns full details if subscribed, preview otherwise
     */
    public Object getPlanDetails(Long planId, Long userId, String userRole) {
        FitnessPlan plan = fitnessPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));

        // If user is TRAINER and owns the plan, return full details
        if ("TRAINER".equals(userRole) && plan.getTrainer().getId().equals(userId)) {
            return mapToFullResponse(plan);
        }

        // If user is USER and subscribed, return full details
        if ("USER".equals(userRole) && subscriptionService.isSubscribed(userId, planId)) {
            return mapToFullResponse(plan);
        }

        // Otherwise, return preview only
        return mapToPreview(plan);
    }

    /**
     * Map to full response (for subscribers and plan owners)
     */
    private FitnessPlanResponse mapToFullResponse(FitnessPlan plan) {
        return new FitnessPlanResponse(
            plan.getId(),
            plan.getTitle(),
            plan.getDescription(),
            plan.getPrice(),
            plan.getDuration(),
            plan.getTrainer().getId(),
            plan.getTrainer().getFullName(),
            plan.getCreatedAt().format(DATE_FORMATTER)
        );
    }

    /**
     * Map to preview (for non-subscribers)
     */
    private FitnessPlanPreview mapToPreview(FitnessPlan plan) {
        return new FitnessPlanPreview(
            plan.getId(),
            plan.getTitle(),
            plan.getPrice(),
            plan.getTrainer().getId(),
            plan.getTrainer().getFullName()
        );
    }
}
