package com.example.project3.config;

import com.example.project3.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private Long id;
    private String username;
    private String password;
    @Getter
    private String email;
    @Getter
    private String phone;

    private String authorities;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
               .toList();
    }

    public static CustomUserDetails fromEntity(UserEntity entity) {
        CustomUserDetails details = new CustomUserDetails();
        details.id = entity.getId();
        details.username = entity.getUsername();
        details.password = entity.getPassword();
        details.email = entity.getEmail();
        details.phone = entity.getPhone();
        details.authorities = entity.getAuthorities();
        return details;
    }

}
