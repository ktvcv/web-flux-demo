package com.example.webfluxdemo.security;

import com.example.webfluxdemo.entity.UserEntity;
import com.example.webfluxdemo.exceptions.AuthException;
import com.example.webfluxdemo.repo.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SecurityService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.password.secret}")
    private  String secret;
    @Value("${jwt.password.expiration}")
    private  Integer expiration;
    @Value("${jwt.password.issuer}")
    private  String issuer;

    public SecurityService(final UserRepo userRepo, final PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    private TokenDetail generateToken(final UserEntity userEntity) {
        final Map<String, Object> claims = new HashMap<>() {{
            put("role", userEntity.getRole());
            put("username", userEntity.getUsername());
        }};
        return generateToken(claims, userEntity.getId().toString());
    }

    private TokenDetail generateToken(final Map<String, Object> claims, final String subject) {
        final long expirationTimeInMillis = expiration * 1000L;
        final Date expirationDate = new Date(new Date().getTime() + expirationTimeInMillis);

        return generateToken(expirationDate, claims, subject);
    }

    private TokenDetail generateToken(final Date expirationDate, final Map<String, Object> claims, final String subject) {
        final Date date = new Date();

        final String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(date)
            .setIssuer(issuer)
            .setExpiration(expirationDate)
            .setSubject(subject)
            .setId(UUID.randomUUID().toString())
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secret.getBytes()))
            .compact();

        return new TokenDetail()
            .setToken(token)
            .setIssuedAt(date)
            .setExpiredAt(expirationDate);
    }

    public Mono<TokenDetail> authorize(final String username, final String password) {
        return userRepo.findByUsername(username)
            .flatMap(user -> {
                if (!user.isEnable()) {
                    return Mono.error(new AuthException("The user is not enabled", "ACCOUNT_DISABLED"));
                }
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    return Mono.error(new AuthException("The username or password is wrong", "WRONG"));
                }

                return Mono.just(generateToken(user)
                    .setUserId(user.getId())
                );
            })
            .switchIfEmpty(Mono.error(new AuthException("The username or password is wrong", "WRONG")));
    }
}
