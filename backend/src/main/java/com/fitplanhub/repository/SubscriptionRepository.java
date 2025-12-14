package com.fitplanhub.repository;

import com.fitplanhub.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Subscription entity
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    /**
     * Find all subscriptions for a specific user
     */
    List<Subscription> findByUserId(Long userId);
    
    /**
     * Check if a user is subscribed to a specific plan
     */
    boolean existsByUserIdAndPlanId(Long userId, Long planId);
    
    /**
     * Find subscription by user ID and plan ID
     */
    Optional<Subscription> findByUserIdAndPlanId(Long userId, Long planId);
    
    /**
     * Get all fitness plans subscribed by a user
     */
    @Query("SELECT s FROM Subscription s JOIN FETCH s.plan WHERE s.user.id = :userId")
    List<Subscription> findSubscriptionsWithPlansByUserId(@Param("userId") Long userId);
}
