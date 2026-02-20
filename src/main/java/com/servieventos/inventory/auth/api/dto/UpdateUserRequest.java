package com.servieventos.inventory.auth.api.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
        String name,
        @Email String email,
        String role,
        Boolean enabled
) {}
