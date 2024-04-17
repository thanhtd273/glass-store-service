package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class ColorInfo {
    private Long colorId;

    private String name;

    private String hexCode;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(hexCode);
    }
}
