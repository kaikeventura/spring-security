package com.kaikeventura.spring_security.dto;

import lombok.Getter;

@Getter
public class TokenDTO {

    private final String token;

    public TokenDTO(final String token) {
        this.token = "Bearer " + token;
    }
}
