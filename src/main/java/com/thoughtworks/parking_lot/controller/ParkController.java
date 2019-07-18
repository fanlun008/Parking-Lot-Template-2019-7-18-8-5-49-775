package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.Parkinglot;
import com.thoughtworks.parking_lot.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkinglots")
public class ParkController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("")
    public Parkinglot addParkinglot(@RequestBody Parkinglot parkinglot) {
        Parkinglot parkinglot1 = parkingService.saveParkinglot(parkinglot);
        System.out.println("addParkinglot...");
        return parkinglot1;
    }

    @DeleteMapping("/{id}")
    public String deleteParkinglot(@PathVariable(value = "id") String id) {
        return parkingService.deleteParkinglot(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Parkinglot> findAll(@RequestParam("page")Integer page, @RequestParam("pageSize")Integer pageSize){
        List<Parkinglot> all = parkingService.findAll(page, pageSize);
        return all;
    }

}
