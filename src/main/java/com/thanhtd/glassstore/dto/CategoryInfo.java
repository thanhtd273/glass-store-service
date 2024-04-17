package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class CategoryInfo {
    private String name;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name);
    }
}
