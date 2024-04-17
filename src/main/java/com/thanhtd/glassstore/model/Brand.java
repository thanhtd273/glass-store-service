package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "brand")
@NamedQuery(name = "Brand.findALl", query = "SELECT u FROM Brand u WHERE u.status <> 0")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
