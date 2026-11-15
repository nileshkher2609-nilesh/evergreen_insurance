package com.example.funeral_platform.auth;


import com.example.funeral_platform.auth.dto.CreateAgentAccountRequest;
import com.example.funeral_platform.auth.dto.LoginRequest;
import com.example.funeral_platform.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/agents")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAgentAccount(
            @Valid @RequestBody CreateAgentAccountRequest request) {

        authService.createAgentAccount(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Agent account created successfully");
    }
}
