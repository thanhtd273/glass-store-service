package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "_action")
@NamedQuery(name = "Action.findAll", query = "SELECT u FROM Action u WHERE u.status <> 0")
public class Action implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Long actionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private Integer code;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
