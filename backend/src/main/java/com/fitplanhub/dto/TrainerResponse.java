package com.fitplanhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Trainer Response DTO
 * Returns trainer information without sensitive data (no password, email)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponse {
    private Long trainerId;
    private String name;
    private String specializations;
    private Integer experience;
    private String bio;
    private boolean isFollowing; // Whether current user is following this trainer
}
