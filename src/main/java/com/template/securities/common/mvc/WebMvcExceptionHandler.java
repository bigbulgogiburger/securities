package com.template.securities.common.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.SecuritiesApplication;
import com.template.securities.common.exception.*;
import com.template.securities.common.mvc.context.CustomHttpServletRequestWrapper;
import com.template.securities.common.mvc.model.ErrorResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class WebMvcExceptionHandler extends ResponseEntityExceptionHandler {

    @Setter(AccessLevel.PROTECTED)
    protected static Clock CLOCK = SecuritiesApplication.DEFAULT_CLOCK;

    private final MessageSource messageSource;

    private final ObjectMapper objectMapper;

    @ExceptionHandler({
            ResourceNotFoundException.class,
            InvalidDataException.class,
            DeniedException.class,
            Exception.class
    })
    public ResponseEntity<Object> handleApplicationException(Exception e, WebRequest request){
        writeLogError(request,e);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        if(e instanceof  ResourceNotFoundException){
            ResourceNotFoundException notFoundException = (ResourceNotFoundException) e;
            return createErrorResponse(request, notFoundException,path, HttpStatus.NOT_FOUND);
        }else if(e instanceof InvalidDataException){
            InvalidDataException invalidDataException = (InvalidDataException) e;
            return createErrorResponse(request,invalidDataException,path,HttpStatus.BAD_REQUEST);
        }else if(e instanceof DeniedException){
            DeniedException deniedException = (DeniedException) e;
            return createErrorResponse(request,deniedException,path,HttpStatus.FORBIDDEN);
        }
        String message = messageSource.getMessage(CommonErrorCodes.INTERNAL_SERVER_ERROR,null, request.getLocale());
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(CLOCK),message,path),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> createErrorResponse(WebRequest request, UncheckedMainException exception, String path, HttpStatus status) {
        String message = messageSource.getMessage(exception.getErrorCode(),exception.getArgs(),request.getLocale());
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(CLOCK),message,path),status);
    }


    private void writeLogError(WebRequest webRequest, Exception cause){
        HttpServletRequest servletRequest = ((ServletWebRequest) webRequest).getRequest();
        if(servletRequest instanceof CustomHttpServletRequestWrapper){
            CustomHttpServletRequestWrapper wrappedRequest = (CustomHttpServletRequestWrapper) servletRequest;
            writeLogError(wrappedRequest,cause);
        }
    }

    private void writeLogError(CustomHttpServletRequestWrapper wrappedRequest , Exception cause){
        try {
            String tree = objectMapper.readTree(wrappedRequest.getContentAsByteArray()).toString();
            log.error("["+wrappedRequest.getKey()+"] Handling Exception ("+cause.getClass().getName()+"(, requestBody: "+tree,cause);
        }catch (IOException e){
            log.error("["+wrappedRequest.getKey()+"] Handling Exception ("+cause.getClass().getName()+"(, requestBody: "+new String(wrappedRequest.getContentAsByteArray()),cause);

        }
    }
}
