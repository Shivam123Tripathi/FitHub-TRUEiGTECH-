package com.fitplanhub.repository;

import com.fitplanhub.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Trainer Repository
 * Provides database operations for Trainer entity
 */
@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    
    /**
     * Find trainer by email address
     * Used for checking if email already exists during signup
     * 
     * @param email the email to search for
     * @return Optional containing trainer if found
     */
    Optional<Trainer> findByEmail(String email);
    
    /**
     * Check if a trainer exists with the given email
     * 
     * @param email the email to check
     * @return true if trainer exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Search trainers by name or specialization
     * 
     * @param keyword the search keyword
     * @return list of matching trainers
     */
    @Query("SELECT t FROM Trainer t WHERE LOWER(t.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.specializations) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Trainer> searchByNameOrSpecialization(@Param("keyword") String keyword);
}
