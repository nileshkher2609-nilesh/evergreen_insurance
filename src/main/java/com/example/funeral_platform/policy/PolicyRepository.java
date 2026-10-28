package com.example.funeral_platform.policy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy,Long> {

    List<Policy> findByAssignedAgentId(Long agentId);
}
