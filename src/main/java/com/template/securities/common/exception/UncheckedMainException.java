package com.template.securities.common.exception;

import lombok.Getter;


/**
 * 이곳에서 쓰일 모든 unchecked Exception의 parent
 */
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
