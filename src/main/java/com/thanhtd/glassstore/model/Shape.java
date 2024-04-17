package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "shape")
@NamedQuery(name = "Shape.findAll", query = "SELECT u FROM Shape u WHERE u.status <> 0")
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shape_id")
    private Long shapeId;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
