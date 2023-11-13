package com.template.securities.common.mvc.filter;

import com.template.securities.common.mvc.context.CustomHttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * filter -> OnePerRequestFilter를 상속받고, 해당 클래스가 bean으로 등록이 되었다면(Component)
 * request는 이곳을 무조건 거치게 된다.
 * 이 클래스의 용도는 request가 Spring으로 인입되기 전에, 한 번 감싸준다.
 * 감싸주는 것은 request로 해당 리퀘스트에 UUID로 구성된 String key를 붙여준다.
 * filterChain의 doFilter를 하지 않으면
 * 세상이 멸망하므로, 필터를 다룬다면 꼭 doFilter를 호출할 것.
 */
@Component
public class CustomRequestWrappingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request, UUID.randomUUID().toString());
        filterChain.doFilter(requestWrapper,response);
    }
}
