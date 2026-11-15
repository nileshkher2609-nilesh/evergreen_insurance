package com.example.funeral_platform.controller;

import com.example.funeral_platform.dto.request.PolicyRequest;
import com.example.funeral_platform.dto.response.PolicyResponse;
import com.example.funeral_platform.policy.Policy;
import com.example.funeral_platform.policy.PolicyReviewRequest;
import com.example.funeral_platform.policy.PolicyStatus;
import com.example.funeral_platform.service.PolicyService;
import com.example.funeral_platform.user.UserAccount;
import com.example.funeral_platform.user.UserRepository;
import com.example.funeral_platform.user.UserRole;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;
    private final UserRepository userRepository;

    public PolicyController(PolicyService policyService, UserRepository userRepository) {
        this.policyService = policyService;
        this.userRepository = userRepository;
    }

    // The reason why we added the user repository is we need authenticated user from spring security.


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    public PolicyResponse getPolicyById(@PathVariable Long policyId, @AuthenticationPrincipal UserDetails currentUser) {

        UserAccount account = userRepository
                .findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User account not found"));

        if(account.getRole() == UserRole.ADMIN){
            return policyService.getPolicyById(policyId);
        }

        if(account.getAgent() == null){
            throw new IllegalArgumentException("Agent Profile not linked");
        }
        return policyService.getPolicyByIdForAgent(
                policyId,
                account.getAgent().getId()
        );
    }

    @PutMapping("/{policyId}/assign/{agentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public PolicyResponse assignPolicy(@Valid @PathVariable Long policyId, @PathVariable Long agentId) {
        return policyService.assignPolicy(policyId, agentId);
    }

    @PutMapping("/{policyId}/review")
    @PreAuthorize("hasRole('AGENT')")
    public PolicyResponse reviewPolicy(@Valid @PathVariable Long policyId, @Valid @RequestBody PolicyReviewRequest request, @AuthenticationPrincipal UserDetails currentUser) {


        UserAccount account = userRepository
                .findByEmail(currentUser.getUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException("User account not found"));

        if(account.getAgent() == null){
            throw new IllegalArgumentException("Agent Profile not linked");
        }

        return policyService.reviewPolicy(policyId,request,account.getAgent().getId());
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
