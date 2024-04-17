package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_shape")
public class ProductShape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "shape_id")
    private Long shapeId;
}
