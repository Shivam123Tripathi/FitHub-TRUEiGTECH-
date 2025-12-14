package com.fitplanhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for fitness plan preview (limited details for non-subscribers)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessPlanPreview {
    private Long id;
    private String title;
    private Double price;
    private Long trainerId;
    private String trainerName;
}
