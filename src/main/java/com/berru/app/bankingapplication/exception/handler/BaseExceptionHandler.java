package com.berru.app.bankingapplication.exception.handler;

import com.berru.app.bankingapplication.exception.BaseException;
import com.berru.app.bankingapplication.exception.IBaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<IBaseException> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ex, ex.getHttpStatus());
    }
}