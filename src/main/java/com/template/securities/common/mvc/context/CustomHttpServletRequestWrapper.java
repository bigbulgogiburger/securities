package com.template.securities.common.mvc.context;

import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

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
