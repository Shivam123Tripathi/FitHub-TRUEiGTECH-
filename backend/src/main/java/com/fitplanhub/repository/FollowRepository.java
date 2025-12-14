package com.fitplanhub.repository;

import com.fitplanhub.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Follow entity
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    /**
     * Find all trainers followed by a specific user
     */
    List<Follow> findByUserId(Long userId);
    
    /**
     * Check if a user is following a specific trainer
     */
    boolean existsByUserIdAndTrainerId(Long userId, Long trainerId);
    
    /**
     * Find follow relationship by user ID and trainer ID
     */
    Optional<Follow> findByUserIdAndTrainerId(Long userId, Long trainerId);
    
    /**
     * Get all trainers followed by a user with trainer details
     */
    @Query("SELECT f FROM Follow f JOIN FETCH f.trainer WHERE f.user.id = :userId")
    List<Follow> findFollowsWithTrainersByUserId(@Param("userId") Long userId);
    
    /**
     * Count followers for a specific trainer
     */
    long countByTrainerId(Long trainerId);
}
