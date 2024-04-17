package com.thanhtd.glassstore.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.sql.Time;

@Data
public class StoreInfo {
    private String name;

    private Long cityId;

    private Long districtId;

    private Long wardId;

    private String address;

    private String phoneNumber;

    private Time openTime;

    private Time closeTime;

    private Integer status;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(cityId) || ObjectUtils.isEmpty(districtId) ||
                ObjectUtils.isEmpty(wardId) || ObjectUtils.isEmpty(openTime) || ObjectUtils.isEmpty(closeTime) || ObjectUtils.isEmpty(phoneNumber);
    }

}
