package com.fitplanhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Response DTO
 * Returns JWT token and user information after successful login
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private Long id;
    private String email;
    private String role;
    private String fullName;

    /**
     * Constructor for basic response with token, id, and role
     */
    public LoginResponse(String token, Long id, String role) {
        this.token = token;
        this.id = id;
        this.role = role;
    }
}
