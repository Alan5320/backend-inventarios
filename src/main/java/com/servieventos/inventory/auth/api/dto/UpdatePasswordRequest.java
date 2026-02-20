package com.servieventos.inventory.auth.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
        @NotBlank String newPassword
) {}
