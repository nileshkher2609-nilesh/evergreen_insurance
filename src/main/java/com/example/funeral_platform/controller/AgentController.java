package com.example.funeral_platform.controller;


import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.dto.request.AgentRequest;
import com.example.funeral_platform.dto.response.AgentResponse;
import com.example.funeral_platform.service.AgentService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping
    public Page<AgentResponse> getAllAgents(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return agentService.getAllAgents(pageable);
    }

    @PostMapping
    public AgentResponse agents(@Valid @RequestBody AgentRequest request) {
        return agentService.createAgent(request);
    }

}
