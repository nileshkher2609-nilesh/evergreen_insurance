package com.example.funeral_platform.controller;

import com.example.funeral_platform.dto.request.PolicyRequest;
import com.example.funeral_platform.dto.response.PolicyResponse;
import com.example.funeral_platform.policy.Policy;
import com.example.funeral_platform.policy.PolicyReviewRequest;
import com.example.funeral_platform.policy.PolicyStatus;
import com.example.funeral_platform.service.PolicyService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    public PolicyResponse createPolicy(@Valid @RequestBody PolicyRequest request) {
        return policyService.createPolicy(request);
    }
//
//    @GetMapping
//    public Page<PolicyResponse> getAllPolicies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
//
//        Sort sort = direction.equalsIgnoreCase("desc")
//                ? Sort.by(sortBy).descending()
//                : Sort.by(sortBy).ascending();
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return policyService.getAllPolicies(pageable);
//    }

    @GetMapping("/{policyId}")
    public PolicyResponse getPolicyById(@PathVariable Long policyId) {
        return policyService.getPolicyById(policyId);
    }

    @PutMapping("/{policyId}/assign/{agentId}")
    public PolicyResponse assignPolicy(@Valid @PathVariable Long policyId, @PathVariable Long agentId) {
        return policyService.assignPolicy(policyId, agentId);
    }

    @PutMapping("/{policyId}/review")
    public PolicyResponse reviewPolicy(@Valid @PathVariable Long policyId, @RequestBody PolicyReviewRequest request) {
        return policyService.reviewPolicy(policyId, request);

    }

    @GetMapping
    public Page<PolicyResponse> searchPolicies(

            @RequestParam(required = false)
            String keyword,

            @RequestParam(required = false)
            PolicyStatus status,

            @RequestParam(required = false)
            Long agentId,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100");
        }

        Set<String> allowedSortFields = Set.of(
                "id",
                "policyNumber",
                "applicantName",
                "policyType",
                "coverageAmount",
                "status"
        );

        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field");
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException(
                    "Direction must be asc or desc");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return policyService.searchPolicies(
                keyword,
                status,
                agentId,
                pageable
        );
    }

}
