package com.fitplanhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for fitness plan response (full details)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessPlanResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Integer duration;
    private Long trainerId;
    private String trainerName;
    private String createdAt;
}
