package com.fitplanhub.service;

import com.fitplanhub.dto.FitnessPlanRequest;
import com.fitplanhub.dto.FitnessPlanResponse;
import com.fitplanhub.entity.FitnessPlan;
import com.fitplanhub.entity.Trainer;
import com.fitplanhub.repository.FitnessPlanRepository;
import com.fitplanhub.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

// manages trainer's fitness plan CRUD operations
@Service
public class TrainerPlanService {

    @Autowired
    private FitnessPlanRepository fitnessPlanRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // create new plan for a trainer
    public FitnessPlanResponse createPlan(Long trainerId, FitnessPlanRequest request) {
        Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new RuntimeException("Trainer not found"));

        FitnessPlan plan = new FitnessPlan();
        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.setPrice(request.getPrice());
        plan.setDuration(request.getDuration());
        plan.setTrainer(trainer);

        FitnessPlan savedPlan = fitnessPlanRepository.save(plan);
        return mapToResponse(savedPlan);
    }

    // get all plans created by this trainer
    public List<FitnessPlanResponse> getTrainerPlans(Long trainerId) {
        List<FitnessPlan> plans = fitnessPlanRepository.findByTrainerIdOrderByCreatedAtDesc(trainerId);
        return plans.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    // update plan - must be owned by this trainer
    public FitnessPlanResponse updatePlan(Long trainerId, Long planId, FitnessPlanRequest request) {
        FitnessPlan plan = fitnessPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));

        // make sure trainer owns this plan
        if (!plan.getTrainer().getId().equals(trainerId)) {
            throw new RuntimeException("Unauthorized: You can only update your own plans");
        }

        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.setPrice(request.getPrice());
        plan.setDuration(request.getDuration());

        FitnessPlan updatedPlan = fitnessPlanRepository.save(plan);
        return mapToResponse(updatedPlan);
    }

    // delete plan - ownership check
    public void deletePlan(Long trainerId, Long planId) {
        FitnessPlan plan = fitnessPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));

        // verify ownership
        if (!plan.getTrainer().getId().equals(trainerId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own plans");
        }

        fitnessPlanRepository.delete(plan);
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
