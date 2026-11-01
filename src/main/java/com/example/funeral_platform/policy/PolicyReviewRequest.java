package com.example.funeral_platform.policy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PolicyReviewRequest {

    @NotNull(message = "Review status is required")
    private PolicyStatus status;

    @NotBlank(message = "Review comment is required")
    @Size(max = 500,
            message = "Review comment cannot exceed 500 characters")
    private String reviewComment;

    public PolicyReviewRequest() {
    }

}
