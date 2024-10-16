package com.example.project3.config;

import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserConfig {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public CommandLineRunner createUser() {

        return args -> {
            if (!repository.existsByUsername("admin")) {
                UserEntity admin = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("1234"))
                        .nickname("diu")
                        .name("diu")
                        .age(20)
                        .email("diu@example.com")
                        .phone("0101234")
                        .authorities("ROLE_ADMIN,READ,WRITE")
                        .build();
                repository.save(admin);
            }


            if (!repository.existsByUsername("alex")) {
                UserEntity user = UserEntity.builder()
                        .username("alex")
                        .password(passwordEncoder.encode("1234"))
                        .nickname("user")
                        .name("user")
                        .age(30)
                        .email("user1@example.com")
                        .phone("0105678")
                        .authorities("ROLE_USER")
                        .build();
                this.repository.save(user);
            }

            if (!repository.existsByUsername("owner")) {
                UserEntity user = UserEntity.builder()
                        .username("owner")
                        .password(passwordEncoder.encode("1234"))
                        .nickname("owner")
                        .name("owner")
                        .age(30)
                        .email("user1@example.com")
                        .phone("0102345")
                        .authorities("ROLE_BUSINESS")
                        .build();
                this.repository.save(user);
            }


        };
    }
}
