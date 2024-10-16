package com.example.project3.user;

import com.example.project3.config.CustomUserDetails;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<UserEntity> optionalUser =
                repository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);

        return CustomUserDetails.fromEntity(optionalUser.get());

    }


    public UserEntity findByUsername(String username) {
        Optional<UserEntity> optionalUser = repository.findByUsername(username);
        if (optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            return user;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
