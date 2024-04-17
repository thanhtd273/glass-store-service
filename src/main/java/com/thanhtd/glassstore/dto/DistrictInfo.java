package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class DistrictInfo {
    private Long districtId;

    private String cityName;

    private String name;

    private String code;

    private String description;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(code) || ObjectUtils.isEmpty(cityName);
    }
}
