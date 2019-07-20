package com.thoughtworks.parking_lot.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {

    private Integer code;
    private String msg;
    private Object result;

    public static ApiResponse ofMsg(Integer code, String msg) {
        return new ApiResponse(code, msg, null);
    }

    public ApiResponse(Integer code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
}
