package com.example.webfluxdemo.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserAuthenticationBearer {

    public static Mono<Authentication> create(final JwtHandler.VerificationResult verificationResult){
        Claims claims = verificationResult.claims;
        final String subject = claims.getSubject();

        String role = claims.get("role", String.class);
        String username = claims.get("username", String.class);

        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(role));

        final CustomPrincipal customPrincipal = new CustomPrincipal(Long.parseLong(subject), username);

        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(customPrincipal, null, authorityList));

    }
}
