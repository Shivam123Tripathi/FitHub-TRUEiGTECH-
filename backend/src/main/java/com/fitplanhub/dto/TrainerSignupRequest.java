package com.fitplanhub.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Trainer Signup
 * Used to receive trainer registration data from frontend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSignupRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Years of experience is required")
    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience must be less than 50")
    private Integer yearsOfExperience;

    @NotBlank(message = "Specializations are required")
    private String specializations;

    private String bio; // Optional field
}
