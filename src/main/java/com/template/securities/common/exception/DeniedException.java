package com.template.securities.common.exception;

public class DeniedException extends UncheckedMainException{


    public DeniedException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public String getMessage() {
        return "request denied";
    }
}
