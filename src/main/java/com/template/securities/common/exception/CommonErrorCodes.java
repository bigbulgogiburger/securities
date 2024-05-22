package com.template.securities.common.exception;


/**
 * ERROR MESSAGES
 * 해당 String은 error로 handling이 되었을 때에 message.properties에서
 * key로서 사용된다.
 * */
public class CommonErrorCodes {

    public static final String NOT_FOUND_ERROR = "error.not.found";
    public static final String BAD_REQUEST_ERROR = "error.bad.request";
    public static final String INTERNAL_SERVER_ERROR = "error.server";
    public static final String UNAUTHORIZED_ERROR = "error.unauthorized";
    public static final String NOT_VALID_PASSWORD_ERROR = "error.bad.password";
}
