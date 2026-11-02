package com.example.funeral_platform.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PolicyRequest {


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
}
