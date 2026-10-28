package com.example.funeral_platform.controller;

import com.example.funeral_platform.policy.Policy;
import com.example.funeral_platform.policy.PolicyReviewRequest;
import com.example.funeral_platform.service.PolicyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController (PolicyService policyService){
        this.policyService = policyService;
    }
    @PostMapping
    public Policy createPolicy (@RequestBody Policy policy){
        return policyService.createPolicy(policy);
    }
    @GetMapping
    public List<Policy> getAllPolicies(){
        return policyService.getAllPolicies();

    }

    @PutMapping("/{policyId}/assign/{agentId}")
    public Policy assignPolicy(@PathVariable Long policyId, @PathVariable Long agentId){
        return policyService.assignPolicy(policyId,agentId);
    }

    @PutMapping("/{policyId}/review")
    public Policy reviewPolicy(@PathVariable Long policyId, @RequestBody PolicyReviewRequest request){
        return policyService.reviewPolicy(policyId,request);

    }
}
