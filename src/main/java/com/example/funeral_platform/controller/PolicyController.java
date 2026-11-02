package com.example.funeral_platform.controller;

import com.example.funeral_platform.dto.request.PolicyRequest;
import com.example.funeral_platform.dto.response.PolicyResponse;
import com.example.funeral_platform.policy.Policy;
import com.example.funeral_platform.policy.PolicyReviewRequest;
import com.example.funeral_platform.service.PolicyService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    public PolicyResponse createPolicy(@Valid @RequestBody PolicyRequest request){
        return policyService.createPolicy(request);
    }

    @GetMapping
    public List<PolicyResponse> getAllPolicies(){
        return policyService.getAllPolicies();
    }

    @GetMapping("/{policyId}")
    public PolicyResponse getPolicyById(@PathVariable Long policyId){
        return policyService.getPolicyById(policyId);
    }

    @PutMapping("/{policyId}/assign/{agentId}")
    public PolicyResponse assignPolicy( @Valid @PathVariable Long policyId, @PathVariable Long agentId){
        return policyService.assignPolicy(policyId,agentId);
    }

    @PutMapping("/{policyId}/review")
    public PolicyResponse reviewPolicy(@Valid @PathVariable Long policyId, @RequestBody PolicyReviewRequest request){
        return policyService.reviewPolicy(policyId,request);

    }

}
