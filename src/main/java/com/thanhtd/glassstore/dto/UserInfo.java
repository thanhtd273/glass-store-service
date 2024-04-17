package com.thanhtd.glassstore.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserInfo implements Serializable {
    private Long userId;

    private String fullName;

    private Date birthday;

    private String phoneNumber;

    private String avatar;

    private List<String> roles;

    private String cityName;

    private String districtName;

    private String wardName;

    private String address;

    private Integer status;


}
