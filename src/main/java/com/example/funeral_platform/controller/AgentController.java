package com.example.funeral_platform.controller;


import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.dto.request.AgentRequest;
import com.example.funeral_platform.dto.response.AgentResponse;
import com.example.funeral_platform.service.AgentService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService){
        this.agentService = agentService;
    }

    @GetMapping
    public List<AgentResponse> getAllAgents(){

        return agentService.getAllAgents();
    }

    @PostMapping
    public AgentResponse agents(@Valid @RequestBody AgentRequest request){
        return agentService.createAgent(request);
    }

}
