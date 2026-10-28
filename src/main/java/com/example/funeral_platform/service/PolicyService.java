package com.example.funeral_platform.service;

import com.example.funeral_platform.agent.AgentRepository;
import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.policy.Policy;
import com.example.funeral_platform.policy.PolicyRepository;
import com.example.funeral_platform.policy.PolicyReviewRequest;
import com.example.funeral_platform.policy.PolicyStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final AgentRepository agentRepository;

    public PolicyService(PolicyRepository policyRepository, AgentRepository agentRepository){

        this.policyRepository = policyRepository;
        this.agentRepository = agentRepository;
    }

    public Policy createPolicy(Policy policy){

        return policyRepository.save(policy);
    }

    public List<Policy> getAllPolicies(){

        return policyRepository.findAll();
    }

    @Transactional
    public Policy assignPolicy(Long policyId, Long agentId){
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() ->
                        new RuntimeException("Policy not Found"));

        Agents agents = agentRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not Found"));

        policy.setAssignedAgent(agents);

        return policyRepository.save(policy);
    }

    @Transactional
    public Policy reviewPolicy(Long policyId, PolicyReviewRequest request){
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy Not Found"));

        if(policy.getStatus() != PolicyStatus.PENDING){
            throw new RuntimeException("Only pending policies can be reviewed");
        }
        if(request.getStatus() != PolicyStatus.APPROVED && request.getStatus() !=  PolicyStatus.DENIED){
            throw new RuntimeException("Review must be correct");
        }

        policy.setStatus(request.getStatus());
        policy.setReviewComment(request.getReviewComment());

        return policyRepository.save(policy);
    }
}
