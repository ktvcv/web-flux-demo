package com.example.webfluxdemo.security;

import com.example.webfluxdemo.entity.UserEntity;
import com.example.webfluxdemo.exceptions.UnauthorizedException;
import com.example.webfluxdemo.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

   private final UserRepo userRepo;

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        final CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        return userRepo.findById(principal.getId())
            .filter(UserEntity::isEnable)
            .switchIfEmpty(Mono.error(new UnauthorizedException("User is unauthorized")))
            .map(userEntity -> authentication);

    }
}
