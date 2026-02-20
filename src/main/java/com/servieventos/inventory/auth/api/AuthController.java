package com.servieventos.inventory.auth.api;

import com.servieventos.inventory.auth.api.dto.LoginRequest;
import com.servieventos.inventory.auth.api.dto.LoginResponse;
import com.servieventos.inventory.auth.application.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest req) {
        String token = authService.login(req.email(), req.password());
        return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
    }
}
