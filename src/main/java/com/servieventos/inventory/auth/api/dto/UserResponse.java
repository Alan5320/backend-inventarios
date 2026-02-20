package com.servieventos.inventory.auth.api.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        String role,
        boolean enabled
) {}
