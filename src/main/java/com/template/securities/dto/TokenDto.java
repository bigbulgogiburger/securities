package com.template.securities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String accessToken;

    private String refreshToken;

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
