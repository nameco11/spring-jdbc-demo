package com.tms.springjdbc.presentation.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BaseResponse<Object> {
    private String errorCode;

    public ErrorResponse(String status,String errorCode, String message, Object data) {
        super(status,message, data);
        this.errorCode = errorCode;
    }
}