package com.example.funeral_platform.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AgentResponse {
    private Long id;

    private String name;

    private String email;
}
