package com.thoughtworks.parking_lot;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.parking_lot.controller.ApiResponse;
import com.thoughtworks.parking_lot.entity.Order;
import com.thoughtworks.parking_lot.entity.Parkinglot;
import com.thoughtworks.parking_lot.repo.OrderRepository;
import com.thoughtworks.parking_lot.repo.ParkinglotRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class OrderTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ParkinglotRepository parkinglotRepository;

    @Test
    public void should_generate_order_when_parkinglot_not_full() throws Exception {
        //given
        String parkingName = UUID.randomUUID().toString().substring(0, 5);
        Parkinglot parkinglot = new Parkinglot(parkingName, 4);
        Parkinglot save_parkinglot = parkinglotRepository.save(parkinglot);

        String requestBody = String.format("{\n" +
                "\t\"lotName\": \"%s\",\n" +
                "\t\"carNumber\": \"粤D7652\"\n" +
                "}", parkingName);

        //when
        String contentAsString = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
        ApiResponse apiResponse = JSON.parseObject(contentAsString, ApiResponse.class);
        //then
        Assertions.assertEquals("生成订单成功", apiResponse.getMsg());
    }


    @Test
    public void should_update_status_when_input_order_id () throws Exception {
        //given
        String parkingName = UUID.randomUUID().toString().substring(0, 5);
        Parkinglot parkinglot = new Parkinglot(parkingName, 4);
        Parkinglot save_parkinglot = parkinglotRepository.save(parkinglot);

        Order order = new Order(parkingName, "粤B76767");
        Order save_order = orderRepository.save(order);
        //when
        String contentAsString = mockMvc.perform(
                put("/orders/{id}", save_order.getId())
        ).andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
        ApiResponse apiResponse = JSON.parseObject(contentAsString, ApiResponse.class);
        //then
        Assertions.assertEquals("OK", apiResponse.getMsg());
        Assertions.assertEquals("OFF", orderRepository.findById(save_order.getId()).get().getStatus());
    }

    @Test
    public void cannot_generate_order_when_parkinglot_full() throws Exception {
        //given
        String parkingName = UUID.randomUUID().toString().substring(0, 5);
        Parkinglot parkinglot = new Parkinglot(parkingName, 1);
        Parkinglot save_parkinglot = parkinglotRepository.save(parkinglot);

        Order order1 = new Order(parkingName, "粤B76767");
        orderRepository.save(order1);

        String requestBody = String.format("{\n" +
                "\t\"lotName\": \"%s\",\n" +
                "\t\"carNumber\": \"粤D7652\"\n" +
                "}", parkingName);

        //when
        String contentAsString2 = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        ApiResponse apiResponse = JSON.parseObject(contentAsString2, ApiResponse.class);
        Assertions.assertEquals("没有足够停车位", apiResponse.getMsg());
    }

}
