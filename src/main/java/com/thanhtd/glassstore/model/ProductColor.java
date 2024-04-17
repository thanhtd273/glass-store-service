package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_color")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "color_id")
    private Long colorId;
}
