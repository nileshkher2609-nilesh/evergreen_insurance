package com.example.funeral_platform.policy;


import com.example.funeral_platform.agent.Agents;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import tools.jackson.core.util.BufferRecycler;

import java.math.BigDecimal;

@Entity
@Table(name = "policies")
@Getter
@Setter
@NoArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Policy number is required")
    @Size(max = 30, message = "Policy number cannot exceed 30 characters")
    @Column(nullable = false, unique = true)
    private String policyNumber;

    @NotBlank(message = "Applicant name is required")
    @Size(min = 2, max = 50,
            message = "Name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String applicantName;

    @NotBlank(message = "Policy Type is required")
    @Column(nullable = false)
    private String policyType;

    @NotNull(message = "Coverage amount is required")
    @Positive(message = "Coverage amount must be greater than zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal coverageAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyStatus status = PolicyStatus.PENDING;



    private String reviewComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id")
    private Agents assignedAgent;
}
