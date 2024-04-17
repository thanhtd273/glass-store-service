package com.thanhtd.glassstore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SessionInfo {
    private UserInfo userInfo;
    private String token;
    private int expireIn;
}
