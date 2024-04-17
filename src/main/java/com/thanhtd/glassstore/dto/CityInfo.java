package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

@Data
public class CityInfo implements Serializable {
    private Long cityId;

    private String name;

    private String code;

    private String description;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(code);
    }
}
