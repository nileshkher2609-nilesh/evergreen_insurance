package com.example.funeral_platform.policy;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class PolicySpecification {
    public static Specification<Policy> filterPolicies(
            String keyword,
            PolicyStatus status,
            Long agentId) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Keyword search: policy number OR applicant name
            if (keyword != null && !keyword.isBlank()) {

                String searchKeyword = "%" +
                        keyword.trim().toLowerCase() + "%";

                Predicate policyNumber =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("policyNumber")),
                                searchKeyword
                        );

                Predicate applicantName =
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("applicantName")),
                                searchKeyword
                        );

                predicates.add(
                        criteriaBuilder.or(policyNumber, applicantName)
                );
            }

            // Filter by policy status
            if (status != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), status)
                );
            }

            // Filter by assigned agent ID
            if (agentId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("assignedAgent").get("id"),
                                agentId
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
