package com.template.securities.common.security.internal;

import com.template.securities.user.domain.Users;
import com.template.securities.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InternalApisAuthenticationProvider {
    private final UserService userService;


    //인증 정보 조회
    public Authentication getAuthentication(String loginId) {

        //Spring Security에서 제공하는 메서드 override해서 사용해야 함
        Users userWithAuthorities = userService.getUserWithAuthorities(loginId);
        return new UsernamePasswordAuthenticationToken(userWithAuthorities.getUsername(), userWithAuthorities.getPassword());
    }
}
