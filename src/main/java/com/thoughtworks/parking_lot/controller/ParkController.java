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
    public List<Parkinglot> findAll(@RequestParam("page")Integer page, @RequestParam(value = "pageSize", defaultValue = "15", required = false)Integer pageSize){
        List<Parkinglot> all = parkingService.findAll(page-1, pageSize);
        return all;
    }

    @GetMapping("/{id}")
    public Parkinglot getDetailInfoById(@PathVariable("id")String id) {
        Parkinglot parkinglot = parkingService.findById(id);
        return parkinglot;
    }

    @PutMapping(value = "/{id}", params = {"capacity"})
    public Parkinglot updateCapacity(@PathVariable("id") String id, @RequestParam("capacity")Integer capacity){
        parkingService.updateCapacityById(id, capacity);
        return parkingService.findById(id);
    }
}
