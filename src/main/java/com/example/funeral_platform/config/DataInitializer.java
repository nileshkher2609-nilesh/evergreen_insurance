package com.example.funeral_platform.config;

import com.example.funeral_platform.user.UserAccount;
import com.example.funeral_platform.user.UserRepository;
import com.example.funeral_platform.user.UserRole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataInitializer {
    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByEmail(adminEmail)) {

                UserAccount admin = new UserAccount();

                admin.setEmail(adminEmail);
                admin.setPassword(
                        passwordEncoder.encode(adminPassword));
                admin.setRole(UserRole.ADMIN);

                userRepository.save(admin);

                System.out.println("Initial admin account created.");
            }
        };
    }
}
