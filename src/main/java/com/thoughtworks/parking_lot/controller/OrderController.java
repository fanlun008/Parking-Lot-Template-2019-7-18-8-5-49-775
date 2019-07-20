package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.Order;
import com.thoughtworks.parking_lot.repo.OrderRepository;
import com.thoughtworks.parking_lot.service.OrderService;
import com.thoughtworks.parking_lot.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ParkingService parkingService;

    @PostMapping("")
    public ApiResponse generateOrder(@RequestBody @Valid Order order) {
        Order saveOrder = orderService.generateOrder(order);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMsg("生成订单成功");
        apiResponse.setResult(saveOrder);
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse closeOrder(@PathVariable("id")String id) {
        String msg = orderService.closeOrder(id);
        return ApiResponse.ofMsg(HttpStatus.OK.value(), msg);
    }

}
