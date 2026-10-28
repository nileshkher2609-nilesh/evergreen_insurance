package com.example.funeral_platform.controller;


import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.service.AgentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService){

        this.agentService = agentService;
    }

    @PostMapping
    public Agents createAgent(@RequestBody Agents agents){

        return agentService.createAgent(agents);
    }


    public List<Agents> getAllAgents(){
        return agentService.getAllAgents();
    }

}
