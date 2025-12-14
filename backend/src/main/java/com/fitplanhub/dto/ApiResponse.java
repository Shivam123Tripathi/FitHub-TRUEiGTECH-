package com.fitplanhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic Response DTO for API responses
 * Used to send consistent responses to the frontend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    
    private boolean success;
    private String message;
    private Object data;

    /**
     * Constructor for success response with data
     */
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Static method to create success response
     */
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    /**
     * Static method to create error response
     */
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }
}
