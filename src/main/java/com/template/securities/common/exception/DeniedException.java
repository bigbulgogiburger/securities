package com.template.securities.common.exception;


/**
 * 허용되지 않은 정보를 가진 회원일때에(unauthorized)
 */
public class DeniedException extends UncheckedMainException{


    public DeniedException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public String getMessage() {
        return "request denied";
    }
}
