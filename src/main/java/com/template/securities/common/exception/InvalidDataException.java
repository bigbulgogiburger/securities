package com.template.securities.common.exception;

public class InvalidDataException extends UncheckedMainException {

    public InvalidDataException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public String getMessage() {
        return "some data is invalid";
    }
}
