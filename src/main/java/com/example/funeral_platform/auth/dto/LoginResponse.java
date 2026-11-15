package com.example.funeral_platform.auth.dto;

import com.example.funeral_platform.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {


    private String token;
    private String tokenType;
    private Long userId;
    private String email;
    private UserRole role;
}
