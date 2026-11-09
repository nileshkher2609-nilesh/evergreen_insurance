package com.example.funeral_platform.policy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy,Long>, JpaSpecificationExecutor<Policy> {

    List<Policy> findByAssignedAgentId(Long agentId);
}
