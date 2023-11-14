package com.template.securities.common.exception;


/**
 * client에서 전달 준 데이터가 비정상적일때에
 */
public class InvalidDataException extends UncheckedMainException {

    public InvalidDataException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public String getMessage() {
        return "some data is invalid";
    }
}
