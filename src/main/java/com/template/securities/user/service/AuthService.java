package com.template.securities.user.service;

import com.template.securities.user.dto.LoginDto;
import com.template.securities.user.dto.RefreshRequest;
import com.template.securities.user.dto.RefreshResponse;
import com.template.securities.user.dto.TokenDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<TokenDto> login(LoginDto loginDto);

    ResponseEntity<RefreshResponse> refreshToken(RefreshRequest request);
}
