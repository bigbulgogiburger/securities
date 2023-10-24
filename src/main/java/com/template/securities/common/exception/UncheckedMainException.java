package com.template.securities.common.exception;

import lombok.Getter;

public class UncheckedMainException extends RuntimeException{

    @Getter
    private final String errorCode;

    @Getter
    private final Object[] args;


    public UncheckedMainException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.args = args;
    }
}
