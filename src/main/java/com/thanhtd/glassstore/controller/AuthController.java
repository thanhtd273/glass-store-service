package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.AuthInfo;
import com.thanhtd.glassstore.dto.SessionInfo;
import com.thanhtd.glassstore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/glass-store")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping(value = "/login")
    public APIResponse login(@RequestBody AuthInfo authInfo) {
        long start = System.currentTimeMillis();
        try {
            SessionInfo sessionInfo = authService.login(authInfo);
            logger.info("POST /glass-store/login success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, sessionInfo);
        } catch (Exception e) {
            logger.error("POST /glass-store/login fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
