package com.thoughtworks.parking_lot.repo;

import com.thoughtworks.parking_lot.entity.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, String>, JpaSpecificationExecutor<Order> {
    @Modifying
    @Query("update Order as t_order set t_order.status = 'OFF', t_order.endTime = now() where t_order.id = :id")
    void closeOrderById(@Param(value = "id") String id);
}
