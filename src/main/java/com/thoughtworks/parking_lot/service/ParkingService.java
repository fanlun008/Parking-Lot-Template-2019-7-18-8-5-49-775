package com.thoughtworks.parking_lot.service;

import com.google.common.collect.Lists;
import com.thoughtworks.parking_lot.ParkingLotException;
import com.thoughtworks.parking_lot.entity.Order;
import com.thoughtworks.parking_lot.entity.Parkinglot;
import com.thoughtworks.parking_lot.repo.ParkinglotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ParkingService {

    @Autowired
    private ParkinglotRepository parkinglotRepository;

    public Parkinglot saveParkinglot(Parkinglot parkinglot) {
        Parkinglot saveOne = parkinglotRepository.save(parkinglot);
        return saveOne;
    }

    public String deleteParkinglot(String id) {
//        parkinglotRepository.deleteByName(name);
        parkinglotRepository.deleteById(id);
        if (!parkinglotRepository.findById(id).isPresent()) {
            return "OK";
        }
        return "fall";
    }

    public List<Parkinglot> findAll(Integer page, Integer pageSize) {
        Pageable pageable = new PageRequest(page, pageSize);
        Page<Parkinglot> all = parkinglotRepository.findAll(pageable);

        return Lists.newArrayList(all.iterator());
    }

    public Parkinglot findById(String id) {
        Optional<Parkinglot> optionalParkinglot = parkinglotRepository.findById(id);
        if (optionalParkinglot.isPresent()) {
            return optionalParkinglot.get();
        }
        return null;
    }

    @Transactional
    public void updateCapacityById(String id, Integer capacity) {
        parkinglotRepository.updateCapacity(id, capacity);
    }

    public Integer getCapacityByLotName(String lotName){
        Parkinglot findLot = parkinglotRepository.findByName(lotName);
        if (findLot == null) {
            throw new ParkingLotException("没有此停车场");
        }
        Integer capacity = parkinglotRepository.getCapacityByLotName(lotName);
        if (capacity == null || capacity == 0) {
            throw new ParkingLotException("没有停车位");
        }
        return capacity;
    }


}
