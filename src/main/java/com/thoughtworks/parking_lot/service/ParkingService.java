package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.Parkinglot;
import com.thoughtworks.parking_lot.repo.ParkinglotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        all.forEach( parkinglot -> {
            System.out.println(parkinglot.getName());
        } );
        return null;
    }

}
