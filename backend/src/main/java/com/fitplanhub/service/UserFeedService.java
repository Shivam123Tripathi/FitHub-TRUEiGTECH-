package com.fitplanhub.service;

import com.fitplanhub.dto.FitnessPlanResponse;
import com.fitplanhub.entity.FitnessPlan;
import com.fitplanhub.repository.FitnessPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// user feed - shows plans from trainers they follow
@Service
public class UserFeedService {

    @Autowired
    private FitnessPlanRepository fitnessPlanRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private SubscriptionService subscriptionService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // get personalized feed based on followed trainers
    public List<FitnessPlanResponse> getUserFeed(Long userId) {
        // which trainers does this user follow?
        List<Long> followedTrainerIds = followService.getFollowedTrainerIds(userId);
        
        if (followedTrainerIds.isEmpty()) {
            return new ArrayList<>();
        }

        // grab plans from all followed trainers
        List<FitnessPlan> feedPlans = new ArrayList<>();
        for (Long trainerId : followedTrainerIds) {
            List<FitnessPlan> trainerPlans = fitnessPlanRepository.findByTrainerIdOrderByCreatedAtDesc(trainerId);
            feedPlans.addAll(trainerPlans);
        }

        // get user's subscriptions for later
        List<Long> subscribedPlanIds = subscriptionService.getSubscribedPlanIds(userId);

        // convert to DTOs
        return feedPlans.stream()
            .map(plan -> {
                FitnessPlanResponse response = mapToResponse(plan);
                // frontend uses this to show "Purchased" badge
                return response;
            })
            .collect(Collectors.toList());
    }

    // get all plans for browsing (not just followed trainers)
    public List<FitnessPlanResponse> getAllPlans() {
        List<FitnessPlan> allPlans = fitnessPlanRepository.findAll();
        return allPlans.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    // convert entity to DTO
    private FitnessPlanResponse mapToResponse(FitnessPlan plan) {
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
}
