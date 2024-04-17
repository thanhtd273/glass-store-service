package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.AuthInfo;
import com.thanhtd.glassstore.dto.UserInfo;
import com.thanhtd.glassstore.model.User;
import com.thanhtd.glassstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/glass-store/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping()
    public APIResponse findAllUsers() {
        long start = System.currentTimeMillis();
        try {
            List<User> userList = userService.findAllUsers();
            logger.info("GET /glass-store/users success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() -start, userList);
        } catch (Exception e) {
            logger.error("GET /glass-store/users fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{userId}")
    public APIResponse findById(@PathVariable("userId") Long userId) {
        long start = System.currentTimeMillis();
        try {
           User user = userService.findByUserId(userId);
            logger.info("GET /glass-store/users/{} success", userId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() -start, user);
        } catch (Exception e) {
            logger.error("GET /glass-store/users/{} fail, error: {}", userId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping()
    public APIResponse createUser(@RequestBody AuthInfo authInfo) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.createUser(authInfo);
            logger.info("POST /glass-store/users success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, user);
        } catch (Exception e) {
            logger.error("POST /glass-store/users fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping()
    public APIResponse updateUser(@RequestBody UserInfo userInfo) {
        long start = System.currentTimeMillis();
        try {
            User user = userService.updateUser(userInfo);
            logger.info("PUT /glass-store/users success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, user);
        } catch (Exception e) {
            logger.error("PUT /glass-store/users fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{userId}")
    public APIResponse deleteUser(@PathVariable("userId") Long userId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = userService.deleteUser(userId);
            logger.info("DELETE /glass-store/users/{} success", userId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/users/{} fail, error: {}", userId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
