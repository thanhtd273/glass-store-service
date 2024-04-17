package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class ActionInfo {
    private Long actionId;

    private String name;

    private int actionCode;

    private String description;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(this.actionCode) || ObjectUtils.isEmpty(this.name);
    }
}
