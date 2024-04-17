package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class ImageInfo {
    private Long imageId;

    private String url;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(url);
    }
}
