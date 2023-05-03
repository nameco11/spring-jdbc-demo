package com.tms.springjdbc.presentation.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse<T> {

    private String status;
    private String message;
    private T data;

    public BaseResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>("success", message, data);
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        return new BaseResponse<>("error", message, data);
    }
}

