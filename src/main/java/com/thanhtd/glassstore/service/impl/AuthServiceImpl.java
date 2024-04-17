package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.AuthInfo;
import com.thanhtd.glassstore.dto.SessionInfo;
import com.thanhtd.glassstore.dto.UserInfo;
import com.thanhtd.glassstore.model.User;
import com.thanhtd.glassstore.service.AuthService;
import com.thanhtd.glassstore.service.JwtService;
import com.thanhtd.glassstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;

    private final UserService userService;

    @Lazy
    private final AuthenticationManager authenticationManager;

    @Override
    public SessionInfo login(AuthInfo authInfo) throws LogicException {
        Authentication userAuth = new UsernamePasswordAuthenticationToken(authInfo.getEmail(), authInfo.getPassword());
        Authentication authentication = authenticationManager.authenticate(userAuth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        if (ObjectUtils.isEmpty(user)) throw new LogicException(ErrorCode.LOGIN_FAIL);

        SessionInfo sessionInfo = new SessionInfo();
        UserInfo userInfo = userService.getUserInfo(user);
        sessionInfo.setUserInfo(userInfo);
        String token = jwtService.generateToken(user);
        sessionInfo.setToken(token);
        sessionInfo.setExpireIn(jwtService.getExpireIn());

        return sessionInfo;
    }

    @Override
    public ErrorCode logout() throws LogicException {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ErrorCode.OK;
    }

}
