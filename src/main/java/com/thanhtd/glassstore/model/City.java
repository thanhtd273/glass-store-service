package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "city")
@NamedQuery(name = "City.findAll", query = "SELECT u FROM City u WHERE u.status <> 0")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
