package com.example.funeral_platform.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<UserAccount, Long> {

    @EntityGraph(attributePaths = "agent")

    Optional<UserAccount> findByEmail(String email);

    boolean existsByEmail(String email);
}