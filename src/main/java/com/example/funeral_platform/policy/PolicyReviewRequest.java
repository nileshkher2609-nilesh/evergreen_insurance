package com.example.funeral_platform.policy;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PolicyReviewRequest {

    private PolicyStatus status;
    private String reviewComment;

    public PolicyReviewRequest() {
    }

}
