package com.example.funeral_platform.auth;

import com.example.funeral_platform.agent.AgentRepository;
import com.example.funeral_platform.agent.Agents;
import com.example.funeral_platform.auth.dto.CreateAgentAccountRequest;
import com.example.funeral_platform.auth.dto.LoginRequest;
import com.example.funeral_platform.auth.dto.LoginResponse;
import com.example.funeral_platform.security.JwtService;
import com.example.funeral_platform.user.UserAccount;
import com.example.funeral_platform.user.UserRepository;
import com.example.funeral_platform.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            AgentRepository agentRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserAccount account = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(account);

        return new LoginResponse(
                token,
                "Bearer",
                account.getId(),
                account.getEmail(),
                account.getRole()
        );
    }

    @Transactional
    public void createAgentAccount(
            CreateAgentAccountRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "An account with this email already exists");
        }

        if (agentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "An agent with this email already exists");
        }

        Agents agent = new Agents();
        agent.setName(request.getName());
        agent.setEmail(request.getEmail());

        Agents savedAgent = agentRepository.save(agent);

        UserAccount account = new UserAccount();
        account.setEmail(request.getEmail());
        account.setPassword(
                passwordEncoder.encode(request.getPassword()));
        account.setRole(UserRole.AGENT);
        account.setAgent(savedAgent);

        userRepository.save(account);
    }
}
