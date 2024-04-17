package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "_permission")
@NamedQuery(name = "Permission.findAll", query = "SELECT u FROM Permission u WHERE u.status <> 0")
public class Permission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

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
