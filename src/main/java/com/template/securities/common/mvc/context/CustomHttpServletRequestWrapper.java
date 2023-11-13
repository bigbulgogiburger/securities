package com.template.securities.common.mvc.context;

import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * wrapper class
 * 기존의 request를 key를 가진 request로 감싸준다.
 * ContentCachingRequestWrapper를 사용하는 이유는,
 * Request는 getInputStream을 통하여 값을 꺼내오게 되면, consume이 되어버린다.
 * 그래서 계속 꺼내기 위해서 ContentCachingRequestWrapper를 사용하여 꺼낼때마다 내부에 복사를 해놓는 형식으로
 * request 로깅을 이어나갈 수 있다.
 */

public class CustomHttpServletRequestWrapper extends ContentCachingRequestWrapper {

    private final String key;
    public CustomHttpServletRequestWrapper(HttpServletRequest request, String key) {
        super(request);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
