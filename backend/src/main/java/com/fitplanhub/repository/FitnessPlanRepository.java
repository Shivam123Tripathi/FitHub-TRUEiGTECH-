package com.fitplanhub.repository;

import com.fitplanhub.entity.FitnessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for FitnessPlan entity
 */
@Repository
public interface FitnessPlanRepository extends JpaRepository<FitnessPlan, Long> {
    
    /**
     * Find all fitness plans created by a specific trainer
     */
    List<FitnessPlan> findByTrainerId(Long trainerId);
    
    /**
     * Find fitness plans by trainer ID ordered by creation date (newest first)
     */
    List<FitnessPlan> findByTrainerIdOrderByCreatedAtDesc(Long trainerId);
}
