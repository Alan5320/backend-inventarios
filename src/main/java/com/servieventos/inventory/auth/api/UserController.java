package com.servieventos.inventory.auth.api;

import com.servieventos.inventory.auth.api.dto.*;
import com.servieventos.inventory.auth.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // ADMINISTRADOR: CRUD completo
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UserResponse>> list() {
        return ResponseEntity.ok(userService.list());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid CreateUserRequest req) {
        return ResponseEntity.ok(userService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest req) {
        return ResponseEntity.ok(userService.update(id, req));
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdatePasswordRequest req) {
        userService.updatePassword(id, req.newPassword());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // EMPLEADO/OPERADOR/ADMINISTRADOR: ver su propio perfil
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','EMPLEADO','OPERADOR')")
    public ResponseEntity<UserResponse> me(Authentication auth) {
        // En el JwtAuthFilter pusimos el principal = email
        return ResponseEntity.ok(userService.me(auth.getName()));
    }
}
