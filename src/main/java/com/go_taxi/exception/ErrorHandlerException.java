package com.go_taxi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorHandlerException {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlerException.class);

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void bindException(BindException be) {
        log.error("BindException = {}", be.getMessage(), be);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void customException(CustomException ce) {
        log.error("CustomException = {}", ce.getMessage(), ce);
    }
}
