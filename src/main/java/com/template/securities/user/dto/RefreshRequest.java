package com.template.securities.user.dto;

import lombok.Data;

@Data
public class RefreshRequest {

    private String accessToken;
    private String refreshToken;
}
