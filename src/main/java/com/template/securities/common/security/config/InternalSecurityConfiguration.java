package com.template.securities.common.security.config;

import com.template.securities.common.security.internal.InternalApisAuthenticationFilter;
import com.template.securities.common.security.internal.InternalApisAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Order(1)
@RequiredArgsConstructor
@Slf4j
@Configuration
public class InternalSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${internal.token}")
    private String internalToken;

    private final InternalApisAuthenticationProvider internalApisAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/internal/**")  // /internal/** 경로에만 적용
                .csrf().disable()  // CSRF 보호 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 관리 비활성화 (무상태)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()  // 모든 요청에 대해 인증 요구
                .and()
                .addFilterBefore(new InternalApisAuthenticationFilter(internalApisAuthenticationProvider,internalToken), UsernamePasswordAuthenticationFilter.class);  // 필터 추가
    }
}
