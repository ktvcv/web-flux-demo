package com.example.webfluxdemo.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetail {

    private Long userId;
    private String token;
    private Date issuedAt;
    private Date expiredAt;
}
