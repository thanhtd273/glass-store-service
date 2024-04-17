package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "_order")
@NamedQuery(name = "Order.findAll", query = "SELECT u FROM Order u WHERE u.status <> 0")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "code")
    private String code;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
