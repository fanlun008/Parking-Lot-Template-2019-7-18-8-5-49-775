package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.ParkingLotException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppErrorControllerAdvice {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse handleResponse(Exception e, HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode!=null) {
            apiResponse.setCode(statusCode);
        } else {
            apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        apiResponse.setMsg(e.getMessage());
        e.printStackTrace();
        return apiResponse;
    }

    @ExceptionHandler(ParkingLotException.class)
    @ResponseBody
    public ApiResponse handleParkingLotException(ParkingLotException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMsg(e.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse handleBindException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMsg(fieldError.getDefaultMessage());
        return apiResponse;
    }

}
