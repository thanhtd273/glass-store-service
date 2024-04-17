package com.thanhtd.glassstore.dto;

import lombok.Data;

@Data
public class ProductSearchCriteria {
    private String name;

    private String color;

    private String shape;

    private String brand;

    private String material;

    private String feature;

    private Integer page;
}
