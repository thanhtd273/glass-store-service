package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.AuthInfo;
import com.thanhtd.glassstore.dto.SessionInfo;

public interface AuthService {
    SessionInfo login(AuthInfo authInfo) throws LogicException;

    ErrorCode logout() throws LogicException;

}
