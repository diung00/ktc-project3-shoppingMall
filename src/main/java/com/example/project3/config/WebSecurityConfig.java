package com.example.project3.config;

import com.example.project3.jwt.JwtTokenFilter;
import com.example.project3.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userService;


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/users/signup", "/users/login", "/error")
                            .anonymous();
                    auth.requestMatchers("/users/myProfile")
                                    .permitAll();
                    auth.requestMatchers("/default/**", "/users/update", "/users/updateProfileImg")
                            .hasRole("DEFAULT");
                    auth.requestMatchers("/users/**", "/purchases/create", "/purchases/cancel/**",  "/purchases/list")
                            .hasRole("USER");
                    auth.requestMatchers("/admin/**")
                            .hasRole("ADMIN");
                    auth.requestMatchers("/shops/**", "/items/**", "/purchases/**",  "/purchases/approve/**")
                                    .hasRole("BUSINESS");
                    auth.anyRequest()
                            .authenticated();
                })
                .formLogin(formLogin -> formLogin
                        .loginPage("/views/login")
                        .defaultSuccessUrl("/views/user-page")
                        .failureUrl("/views/login?fail")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/views/logout")
                        .logoutSuccessUrl("/views/login"))
                .addFilterBefore(
                        new JwtTokenFilter(
                                tokenUtils,
                                userService
                        ),
                        AuthorizationFilter.class
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
