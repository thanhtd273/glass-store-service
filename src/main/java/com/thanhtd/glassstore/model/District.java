package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "district")
@NamedQuery(name = "District.findAll", query = "SELECT u FROM District u WHERE u.status <> 0")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
