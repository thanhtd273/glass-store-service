package com.thanhtd.glassstore.dto;

import com.thanhtd.glassstore.model.Color;
import com.thanhtd.glassstore.model.Shape;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Data
public class ProductInfo {
    private Long productId;

    private String name;

    private String description;

    private Float price;

    private Float discount;

    private String material;

    private Long brandId;

    private Long categoryId;

    private List<Color> colors;

    private List<Shape> shapes;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(price) || ObjectUtils.isEmpty(categoryId) ||
                ObjectUtils.isEmpty(brandId) || ObjectUtils.isEmpty(material);
    }
}
