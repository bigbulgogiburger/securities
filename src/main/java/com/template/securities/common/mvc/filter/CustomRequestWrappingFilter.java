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

@Component
public class CustomRequestWrappingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request, UUID.randomUUID().toString());
        filterChain.doFilter(requestWrapper,response);
    }
}
