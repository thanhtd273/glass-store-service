package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class WardInfo {
    private Long wardId;

    private String cityName;

    private String districtName;

    private String name;

    private String code;

    private String description;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(cityName) || ObjectUtils.isEmpty(districtName) ||
                ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(code);
    }
}
