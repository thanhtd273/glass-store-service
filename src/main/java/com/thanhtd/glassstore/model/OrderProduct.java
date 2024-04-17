package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_amount")
    private Long productAmount;

    @Column(name = "status")
    private Integer status;
}
