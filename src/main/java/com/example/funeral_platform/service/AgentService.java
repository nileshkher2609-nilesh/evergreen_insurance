package com.example.funeral_platform.service;

import com.example.funeral_platform.agent.AgentRepository;
import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.dto.request.AgentRequest;
import com.example.funeral_platform.dto.response.AgentResponse;
import org.aspectj.weaver.loadtime.Agent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository){

        this.agentRepository = agentRepository;
    }

    public AgentResponse createAgent(AgentRequest request){

        Agents agent = convertToEntity(request);
        Agents savedAgent = agentRepository.save(agent);

        return convertToResponse(savedAgent);
    }

    public List<AgentResponse> getAllAgents(){

        return agentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private Agents convertToEntity(AgentRequest request){
        Agents agent = new Agents();

        agent.setName(request.getName());
        agent.setEmail(request.getEmail());

        return agent;
    }

    private AgentResponse convertToResponse(Agents agent){
        AgentResponse response = new AgentResponse();

        response.setId(agent.getId());
        response.setName(agent.getName());
        response.setEmail(agent.getEmail());

        return response;
    }

}
