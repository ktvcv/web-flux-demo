package com.example.webfluxdemo.security;

import com.example.webfluxdemo.exceptions.AuthException;
import com.example.webfluxdemo.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

public class JwtHandler {

    private final String secret;

    public JwtHandler(final String secret) {
        this.secret = secret;
    }

    public Mono<VerificationResult> check(final String token){
        return Mono.just(verify(token))
            .onErrorResume(e -> Mono.error(new UnauthorizedException(e.getMessage())));
    }

    private VerificationResult verify(final String token){
        final Claims claims = getClaimsFromToken(token);
        final Date expirationDate = claims.getExpiration();

        if (expirationDate.before(new Date())){
            throw new RuntimeException("Token is expired");
        }

        return new VerificationResult(claims, token);
    }

    private Claims getClaimsFromToken(final String token){

        return Jwts.parser()
            .setSigningKey(Base64.getEncoder().encode(secret.getBytes()))
            .parseClaimsJws(token)
            .getBody();
    }

    public static class VerificationResult {

        public Claims claims;
        public String token;

        public VerificationResult(final Claims claims, final String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}


