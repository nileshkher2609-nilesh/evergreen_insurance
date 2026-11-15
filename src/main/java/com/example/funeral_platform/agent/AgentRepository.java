package com.example.funeral_platform.agent;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository  extends JpaRepository<Agents,Long> {
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Enter a valid email") String email);
}
