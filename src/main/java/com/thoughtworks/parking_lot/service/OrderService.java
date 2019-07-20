package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.ParkingLotException;
import com.thoughtworks.parking_lot.entity.Order;
import com.thoughtworks.parking_lot.entity.Parkinglot;
import com.thoughtworks.parking_lot.repo.OrderRepository;
import com.thoughtworks.parking_lot.repo.ParkinglotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ParkinglotRepository parkinglotRepository;

    public Order generateOrder(Order order) {
        Parkinglot parkinglot = parkinglotRepository.findByName(order.getLotName());
        if (parkinglot == null) {
            throw new ParkingLotException("没有该停车场");
        }
        return orderRepository.save(order);
    }

    @Transactional
    public String closeOrder(String id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (!byId.isPresent()) {
            throw new ParkingLotException("没有此订单");
        }
        if (byId.get().getStatus().equals("OFF")) {
            throw new ParkingLotException("订单已失效");
        }
        orderRepository.closeOrderById(id);
        return "OK";
    }
}
