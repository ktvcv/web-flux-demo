package com.example.webfluxdemo;

import com.example.webfluxdemo.entity.UserEntity;
import com.example.webfluxdemo.enums.Role;
import com.example.webfluxdemo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity user) {
        final UserEntity userEntity = user
            .setPassword(passwordEncoder.encode(user.getPassword()))
            .setRole(Role.USER)
            .setEnable(true)
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        return userRepository.save(
            userEntity
        ).doOnSuccess(u -> log.info("IN registerUser - user: {} created", u));
    }

    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
