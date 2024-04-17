package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Table(name = "ward")
@NamedQuery(name = "Ward.findAll", query = "SELECT u FROM Ward u WHERE u.status <> 0")
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
