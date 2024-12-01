package com.template.securities.common.security.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class InternalApisAuthenticationFilter extends OncePerRequestFilter {

    private final InternalApisAuthenticationProvider authenticationProvider;

    private final String internalToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String loginId= request.getHeader("loginId");
        String token = request.getHeader("token");

        // memberId와 key가 존재하면 인증 시도
        if (loginId != null && token != null) {
            if(!token.equals(internalToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "bad token");
                return;
            }
            try {
                // AuthenticationProvider를 통해 인증
                Authentication authentication = authenticationProvider.getAuthentication(loginId);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                // 인증 실패 시 401 응답
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        } else {
            // 필수 헤더 누락 시 400 응답
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing loginId or key");
            return;
        }



        // 다음 필터 실행
        filterChain.doFilter(request,response);
    }
}
