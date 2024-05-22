package com.template.securities.user.service;


import com.template.securities.common.exception.CommonErrorCodes;
import com.template.securities.common.exception.InvalidDataException;
import com.template.securities.common.security.jwt.JwtFilter;
import com.template.securities.common.security.jwt.TokenProvider;
import com.template.securities.user.domain.Users;
import com.template.securities.user.dto.LoginDto;
import com.template.securities.user.dto.RefreshRequest;
import com.template.securities.user.dto.RefreshResponse;
import com.template.securities.user.dto.TokenDto;
import com.template.securities.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<TokenDto> login(LoginDto loginDto) {
            Users user = userRepository
                    .findOneWithAuthoritiesByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException(loginDto.getUsername()));
            if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
                throw new InvalidDataException(CommonErrorCodes.NOT_VALID_PASSWORD_ERROR);
            }
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());


            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

            return new ResponseEntity<>(new TokenDto(accessToken,refreshToken), httpHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RefreshResponse> refreshToken(RefreshRequest request) {
        String accessToken = request.getAccessToken();
        return null;
    }
}
