package com.servieventos.inventory.auth.application;

import com.servieventos.inventory.auth.domain.User;
import com.servieventos.inventory.auth.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public String login(String email, String rawPassword) {

        User user = userRepository.findByEmailWithRole(email)
                .filter(User::getEnabled)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return jwtService.generateToken(
                user.getEmail(),
                user.getRole().getName()
        );
    }
}
