package com.example.webfluxdemo.api;

import com.example.webfluxdemo.UserService;
import com.example.webfluxdemo.dto.AuthRequestDto;
import com.example.webfluxdemo.dto.AuthResponseDto;
import com.example.webfluxdemo.dto.UserDto;
import com.example.webfluxdemo.entity.UserEntity;
import com.example.webfluxdemo.mapper.UserMapper;
import com.example.webfluxdemo.security.CustomPrincipal;
import com.example.webfluxdemo.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerUser(entity)
            .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authorize(dto.getUsername(), dto.getPassword())
            .flatMap(tokenDetails -> Mono.just(
                AuthResponseDto.builder()
                    .userId(tokenDetails.getUserId())
                    .token(tokenDetails.getToken())
                    .issuedAt(tokenDetails.getIssuedAt())
                    .expiresAt(tokenDetails.getExpiredAt())
                    .build()
            ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
            .map(userMapper::map);
    }
}
