package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_store")
public class ProductStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "product_amount")
    private Long productAmount;
}
