package com.template.securities.common.exception;

public class ResourceNotFoundException extends UncheckedMainException{


    public ResourceNotFoundException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public String getMessage() {
        return "resource is not found";
    }
}
