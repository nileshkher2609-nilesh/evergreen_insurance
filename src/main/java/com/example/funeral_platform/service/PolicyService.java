package com.example.funeral_platform.service;

import com.example.funeral_platform.agent.AgentRepository;
import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.dto.request.PolicyRequest;
import com.example.funeral_platform.dto.response.PolicyResponse;
import com.example.funeral_platform.exception.AgentNotFoundException;
import com.example.funeral_platform.policy.Policy;
import com.example.funeral_platform.policy.PolicyRepository;
import com.example.funeral_platform.policy.PolicyReviewRequest;
import com.example.funeral_platform.policy.PolicyStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import com.example.funeral_platform.exception.PolicyNotFoundException;

import java.util.List;


@Service
public class PolicyService {

 private final PolicyRepository policyRepository;
 private final AgentRepository agentRepository;

    public PolicyService(PolicyRepository policyRepository, AgentRepository agentRepository){
        this.policyRepository = policyRepository;
        this.agentRepository = agentRepository;
    }


    public PolicyResponse createPolicy(@Valid PolicyRequest request){

        Policy policy = convertToEntity(request);
        Policy savedPolicy = policyRepository.save(policy);



        return convertToResponse(savedPolicy);
    }


    public List<PolicyResponse> getAllPolicies(){

        return policyRepository.findAll()
                .stream()
                 .map(this::convertToResponse)
                .toList();
    }

    public PolicyResponse getPolicyById(Long policyId) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException( "Policy not found with id: " + policyId));

        return convertToResponse(policy);
    }

    @Transactional
    public PolicyResponse assignPolicy(Long policyId, Long agentId){
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException( "Policy not found with id: " + policyId));

        Agents agents = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent Not Found"));

        policy.setAssignedAgent(agents);

        Policy savedPolicy = policyRepository.save(policy);

        return convertToResponse(savedPolicy);

    }

    @Transactional
    public PolicyResponse reviewPolicy(Long policyId, PolicyReviewRequest request){
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException( "Policy not found with id: " + policyId));

        if(policy.getStatus() != PolicyStatus.PENDING){
            throw new RuntimeException("Only pending policies can be reviewed");
        }

        if(request.getStatus() != PolicyStatus.APPROVED && request.getStatus() !=  PolicyStatus.DENIED){
            throw new RuntimeException("Review must be correct");
        }

        policy.setStatus(request.getStatus());
        policy.setReviewComment(request.getReviewComment());
        Policy savedPolicy = policyRepository.save(policy);

        return convertToResponse(savedPolicy);
    }

    private Policy convertToEntity(PolicyRequest request){
        Policy policy = new Policy();

        policy.setPolicyNumber(request.getPolicyNumber());
        policy.setApplicantName(request.getApplicantName());
        policy.setPolicyType(request.getPolicyType());
        policy.setCoverageAmount(request.getCoverageAmount());
        // New policies start with PENDING status
        policy.setStatus(PolicyStatus.PENDING);


        return  policy;
    }

    private PolicyResponse convertToResponse(Policy policy){
        PolicyResponse response = new PolicyResponse();
        response.setId(policy.getId());
        response.setPolicyNumber(policy.getPolicyNumber());
        response.setApplicantName(policy.getApplicantName());
        response.setPolicyType(policy.getPolicyType());
        response.setCoverageAmount(policy.getCoverageAmount());
        response.setStatus(policy.getStatus());
        response.setReviewComment(policy.getReviewComment());

        if (policy.getAssignedAgent() != null){
            response.setAssignedAgentId(policy.getAssignedAgent().getId());
        }

        return response;
    }
}
