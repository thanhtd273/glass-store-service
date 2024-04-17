package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class ShapeInfo {
    private Long shapeId;

    private String name;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name);
    }
}
