package com.template.securities.common.exception;


/**
 * client에서 요청한 정보를 찾을 수 없을 때에
 */
public class ResourceNotFoundException extends UncheckedMainException{


    public ResourceNotFoundException(String errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public String getMessage() {
        return "resource is not found";
    }
}
