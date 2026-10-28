package com.example.funeral_platform.service;

import com.example.funeral_platform.agent.AgentRepository;
import com.example.funeral_platform.agent.Agents;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository){

        this.agentRepository = agentRepository;
    }

    public Agents createAgent(Agents agent){

        return agentRepository.save(agent);
    }

    public List<Agents> getAllAgents(){
        return agentRepository.findAll();
    }
}
