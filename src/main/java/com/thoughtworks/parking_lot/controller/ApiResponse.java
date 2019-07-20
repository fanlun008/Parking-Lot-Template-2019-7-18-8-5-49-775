package com.thoughtworks.parking_lot.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {

    private Integer code;
    private String msg;
    private Object result;

}
