package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class PermissionInfo {
    private Long permissionId;

    private String name;

    private String description;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name);
    }
}
