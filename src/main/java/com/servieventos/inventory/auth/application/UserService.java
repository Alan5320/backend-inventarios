package com.servieventos.inventory.auth.application;

import com.servieventos.inventory.auth.api.dto.*;
import com.servieventos.inventory.auth.domain.Role;
import com.servieventos.inventory.auth.domain.User;
import com.servieventos.inventory.auth.infrastructure.RoleRepository;
import com.servieventos.inventory.auth.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponse> list() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        return toResponse(findUser(id));
    }

    @Transactional
    public UserResponse create(CreateUserRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        Role role = roleRepository.findByName(req.role())
                .orElseThrow(() -> new IllegalArgumentException("Rol inválido"));

        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .passwordHash(passwordEncoder.encode(req.password()))
                .role(role)
                .enabled(true)
                .build();

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest req) {
        User user = findUser(id);

        if (req.email() != null && !req.email().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(req.email())) {
                throw new IllegalArgumentException("El email ya está en uso");
            }
            user.setEmail(req.email());
        }
        if (req.name() != null) user.setName(req.name());

        if (req.role() != null) {
            Role role = roleRepository.findByName(req.role())
                    .orElseThrow(() -> new IllegalArgumentException("Rol inválido"));
            user.setRole(role);
        }

        if (req.enabled() != null) user.setEnabled(req.enabled());

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updatePassword(Long id, String newPassword) {
        User user = findUser(id);
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponse me(String email) {
        User user = userRepository.findByEmailWithRole(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return toResponse(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    private UserResponse toResponse(User u) {
        String roleName = (u.getRole() != null ? u.getRole().getName() : null);
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), roleName, Boolean.TRUE.equals(u.getEnabled()));
    }
}
