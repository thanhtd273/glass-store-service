package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "_role")
@NamedQuery(name = "Role.findAll", query = "SELECT u FROM Role u WHERE u.status <> 0")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

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
