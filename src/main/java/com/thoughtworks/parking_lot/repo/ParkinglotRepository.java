package com.thoughtworks.parking_lot.repo;

import com.thoughtworks.parking_lot.entity.Parkinglot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkinglotRepository extends PagingAndSortingRepository<Parkinglot, String> {

    @Modifying
    @Query("delete from Parkinglot where name= :name")
    void deleteByName(@Param(value = "name") String name);

    Parkinglot findByName(String name);
}
