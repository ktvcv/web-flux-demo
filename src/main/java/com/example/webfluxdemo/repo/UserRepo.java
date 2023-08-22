package com.example.webfluxdemo.repo;

import com.example.webfluxdemo.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends R2dbcRepository<UserEntity, Long> {

    Mono<UserEntity> findByUsername(final String username);

    Mono<UserEntity> findById(final Long id);
}
