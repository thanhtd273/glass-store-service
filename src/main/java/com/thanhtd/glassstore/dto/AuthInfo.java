package com.thanhtd.glassstore.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
public class AuthInfo {
    private String phoneNumber;
    private String email;
    private String password;
    private String confirmPassword;

    public boolean isMissingInfo() {
        return ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(confirmPassword);
    }
}
