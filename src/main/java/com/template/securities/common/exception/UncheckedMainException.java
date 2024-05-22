package com.template.securities.common.exception;

import lombok.Getter;


/**
 * 이곳에서 쓰일 모든 unchecked Exception의 parent
 */
@Getter
public class UncheckedMainException extends RuntimeException{

    private final String errorCode;

    private final Object[] args;


    public UncheckedMainException(String errorCode, Object... args) {
        this.errorCode = errorCode;
        this.args = args;
    }
}
