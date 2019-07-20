package com.thoughtworks.parking_lot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @NotNull(message = "停车场名字不能为空")
    @Column(name = "lot_name", nullable = false)
    private String lotName;

    @NotEmpty(message = "车牌号不能为空")
    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "create_time", updatable = false)
    private Date createTime = new Date();

    @Column(name = "end_time")
    @LastModifiedDate
    private Date endTime;

    @Column
    private String status = "ON";

}
