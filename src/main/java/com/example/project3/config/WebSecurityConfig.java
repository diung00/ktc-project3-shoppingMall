package com.example.project3.config;

import com.example.project3.jwt.JwtTokenFilter;
import com.example.project3.jwt.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userService;
    public WebSecurityConfig(
            JwtTokenUtils tokenUtils,
            UserDetailsService userService
    ) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/token/issue", "/views/login", "/views/signup", "/error")
                            .permitAll();
                    auth.requestMatchers("/default/**")
                            .hasRole("DEFAULT");
                    auth.requestMatchers("/business-requests/create", "/shop/search/**", "/shop/all", "views/homepage")
                            .hasRole("USER");
                    auth.requestMatchers(
                            "/business-requests/view-requests",
                                    "/shop/view-shop-requests",
                                    "/shop/approve-open/**",
                                    "/shop/reject-open/**",
                                    "/shop/approve-close/**",
                                    "/shop/search/**"
                            )
                            .hasRole("ADMIN");
                    auth.requestMatchers("/shop/**", "{userId}/shop/item")
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
