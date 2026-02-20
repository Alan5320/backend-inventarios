package com.servieventos.inventory.auth.application;

import com.servieventos.inventory.auth.domain.Role;
import com.servieventos.inventory.auth.domain.User;
import com.servieventos.inventory.auth.infrastructure.RoleRepository;
import com.servieventos.inventory.auth.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@RequiredArgsConstructor
public class AuthSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role adminRole = upsertRole("ADMINISTRADOR");
        upsertRole("EMPLEADO");
        upsertRole("OPERADOR");

        if (!userRepository.existsByEmail("admin@servieventos.com")) {
            userRepository.save(User.builder()
                    .name("Santiago")
                    .email("admin@servieventos.com")
                    .passwordHash(passwordEncoder.encode("Admin123*"))
                    .role(adminRole)
                    .enabled(true)
                    .build());
        }
    }

    private Role upsertRole(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(Role.builder().name(name).build()));
    }
}
