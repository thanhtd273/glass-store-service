package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "color")
@NamedQuery(name = "Color.findALl", query = "SELECT u FROM Color u WHERE u.status <> 0")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long colorId;

    @Column(name = "name")
    private String name;

    @Column(name = "hex_code")
    private String hexCode;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
