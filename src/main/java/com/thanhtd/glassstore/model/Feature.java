package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "feature")
@NamedQuery(name = "Feature.findALl", query = "SELECT u FROM Feature u WHERE u.status <> 0")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Long featureId;

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
