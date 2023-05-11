package com.tms.springjdbc.presentation.controller;

import com.tms.springjdbc.presentation.web.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse<Object>> handleAllExceptions(Exception ex, WebRequest request) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        response.setMessage("Internal server error");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
