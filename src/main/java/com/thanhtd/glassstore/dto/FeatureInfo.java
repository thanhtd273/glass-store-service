package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class FeatureInfo {
    private Long featureId;

    private String name;

    private String description;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name);
    }
}
