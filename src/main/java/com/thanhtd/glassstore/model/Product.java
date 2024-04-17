package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "product")
@NamedQuery(name = "Product.findALl", query = "SELECT u FROM Product u WHERE u.status <> 0")
public class Product  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "material")
    private String material;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
