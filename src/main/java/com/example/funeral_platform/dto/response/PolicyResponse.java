package com.example.funeral_platform.dto.response;

import com.example.funeral_platform.policy.PolicyStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PolicyResponse {

    private Long id;
    private String policyNumber;
    private String applicantName;
    private String policyType;
    private BigDecimal coverageAmount;
    private PolicyStatus status;
    private String reviewComment;
    private Long assignedAgentId;


}
