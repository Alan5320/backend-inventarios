package com.servieventos.inventory.auth.api.dto;

public record LoginResponse(
        String token,
        String tokenType
) {}
