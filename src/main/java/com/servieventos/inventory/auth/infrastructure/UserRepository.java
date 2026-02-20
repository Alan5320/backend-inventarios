package com.servieventos.inventory.auth.infrastructure;

import com.servieventos.inventory.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
       select u from User u
       join fetch u.role
       where u.email = :email
    """)
    Optional<User> findByEmailWithRole(@Param("email") String email);
}
