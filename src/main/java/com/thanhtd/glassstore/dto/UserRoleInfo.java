package com.thanhtd.glassstore.dto;

import lombok.Data;

@Data
public class UserRoleInfo {
    private Long id;

    private Long userId;

    private Long roleId;

    private UserInfo userInfo;

    private RoleInfo roleInfo;

}
