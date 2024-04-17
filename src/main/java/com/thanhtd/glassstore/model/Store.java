package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name = "store")
@NamedQuery(name = "Store.findAll", query = "SELECT u FROM Store u WHERE u.status <> 0")
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "name")
    private String name;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "open_time")
    private Time openTime;

    @Column(name = "close_time")
    private Time closeTime;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
