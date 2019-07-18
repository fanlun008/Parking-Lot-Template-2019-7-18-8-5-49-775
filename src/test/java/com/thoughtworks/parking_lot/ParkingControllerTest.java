package com.thoughtworks.parking_lot;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.thoughtworks.parking_lot.entity.Parkinglot;
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

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ParkinglotRepository repository;

    @Test
    public void should_add_Parkinglot_when_add_info() throws Exception {
        String uuidName = UUID.randomUUID().toString().substring(0,4);
        String requestBody = String.format("{\n" +
                "\t\"name\": \"%s\",\n" +
                "\t\"capacity\": 45,\n" +
                "\t\"position\": \"biejing12\"\n" +
                "}", uuidName);


        String contentAsString = mockMvc.perform(
                MockMvcRequestBuilders.post("/parkinglots").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        ).andReturn().getResponse().getContentAsString();
        Parkinglot parkinglot = JSON.parseObject(contentAsString, Parkinglot.class);
        System.out.println(parkinglot);
        Assertions.assertEquals(uuidName, repository.findById(parkinglot.getId()).get().getName());
    }

    @Test
    public void should_no_found_when_delete_By_id() throws Exception {
        Parkinglot parkinglot = new Parkinglot().setName(UUID.randomUUID().toString().substring(0,4))
                .setCapacity(100).setPosition("chonqing");
        Parkinglot save = repository.save(parkinglot);

        String contentAsString = mockMvc.perform(delete("/parkinglots/{id}", save.getId()))
                .andReturn().getResponse().getContentAsString();
        Assertions.assertEquals("OK", contentAsString);
    }

    @Test
    public void should_show_limit_15_result_per_page_when_give_pageNum() throws Exception {
        String page = mockMvc.perform(
                get("/parkinglots").param("page", "1")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<Parkinglot> list = JSON.parseObject(page, List.class);
        Assertions.assertSame(15, list.size());
    }

    @Test
    public void should_should_detail_info_By_id() throws Exception {
        Parkinglot parkinglot = new Parkinglot().setName(UUID.randomUUID().toString().substring(0,4))
                .setCapacity(100).setPosition("chonqing");
        Parkinglot save = repository.save(parkinglot);

        String contentAsString = mockMvc.perform(get("/parkinglots/{id}", save.getId()))
                .andReturn().getResponse().getContentAsString();
        Parkinglot _jsonObject = JSON.parseObject(contentAsString, Parkinglot.class);
        Assertions.assertEquals(parkinglot.getPosition(), _jsonObject.getPosition());
    }

    @Test
    public void should_update_capacity_when_call_controller_updatecapacity() throws Exception {
        Parkinglot parkinglot = new Parkinglot().setName(UUID.randomUUID().toString().substring(0,4))
                .setCapacity(100).setPosition("chonqing");
        Parkinglot save = repository.save(parkinglot);
        String capacity = mockMvc.perform(
                put("/parkinglots/{id}", save.getId()).param("capacity", "111")
        ).andReturn().getResponse().getContentAsString();
        Parkinglot parkinglot1 = JSON.parseObject(capacity, Parkinglot.class);
        Assertions.assertSame(111, parkinglot1.getCapacity().intValue());
    }

}
