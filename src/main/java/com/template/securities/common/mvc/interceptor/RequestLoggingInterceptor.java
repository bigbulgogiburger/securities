package com.template.securities.common.mvc.interceptor;

import com.template.securities.common.mvc.context.CustomHttpServletRequestWrapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * request가 들어왔을 때에
 * method, requestURI, Header정보를 가지고 info level의 로그를 남긴다.
 * CustomHttpServletRequestWrapper로 감싸져있는 request일 경우에만 로그를 남긴다.
 */
@Slf4j
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final String DEFAULT_AUTHORIZATION_HEADER_NAME ="authorization";

    @Setter
    private String authorizationHeaderName = DEFAULT_AUTHORIZATION_HEADER_NAME;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request instanceof CustomHttpServletRequestWrapper) {
            CustomHttpServletRequestWrapper requestWrapper = (CustomHttpServletRequestWrapper) request;
            log.info("[{}] method : {}, requestURI : {}", requestWrapper.getKey(), request.getMethod(), requestWrapper.getRequestURI());

            Map<String, List<String>> headers = retrieveHeaderFromRequest(request);
            if(!headers.isEmpty()){
                log.info("[{}] Header: {}",requestWrapper.getKey(),headers);
            }


        }
        return true;
    }

    private Map<String, List<String>> retrieveHeaderFromRequest(HttpServletRequest request) {
        HashMap<String, List<String>> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            Enumeration<String> values = request.getHeaders(headerName);

            if(headerName.equals(authorizationHeaderName)){
                headers.put(headerName,new ArrayList<>());
                while (values.hasMoreElements()){
                    headers.get(headerName).add(values.nextElement());
                }
            }
        }
        return headers;
    }
}

