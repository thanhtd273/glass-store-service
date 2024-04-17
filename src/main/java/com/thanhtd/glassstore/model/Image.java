package com.thanhtd.glassstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "image")
@NamedQuery(name = "Image.findALl", query = "SELECT u FROM Image u WHERE u.status <> 0")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "url")
    private String url;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "status")
    private Integer status;
}
