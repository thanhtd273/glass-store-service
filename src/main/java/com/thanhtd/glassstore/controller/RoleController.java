package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.dto.RoleInfo;
import com.thanhtd.glassstore.model.Role;
import com.thanhtd.glassstore.service.RoleService;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/glass-store/roles")
@RequiredArgsConstructor
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @GetMapping(value = "")
    public APIResponse findAllRoles() {
        long start = System.currentTimeMillis();
        try {
            List<Role> roleList = roleService.findAllRoles();
            logger.info("GET /glass-store/roles success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, roleList);

        } catch (Exception e) {
            logger.error("GET /glass-store/roles fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{roleId}")
    public APIResponse findByRoleId(@PathVariable("roleId") Long roleId) {
        long start = System.currentTimeMillis();
        try {
            Role role = roleService.findByRoleId(roleId);
            logger.info("GET /glass-store/roles/{} success", roleId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, role);

        } catch (Exception e) {
            logger.error("GET /glass-store/roles/{} fail, error: {}", roleId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByName(@RequestParam("name") String name) {
        long start = System.currentTimeMillis();
        try {
            Role role = roleService.findByName(name);
            logger.info("GET /glass-store/roles?name={} success", name);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, role);

        } catch (Exception e) {
            logger.error("GET /glass-store/roles?name={} fail, error: {}", name, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createRole(@RequestBody RoleInfo roleInfo) {
        long start = System.currentTimeMillis();
        try {
            Role role = roleService.createRole(roleInfo);
            logger.info("POST /glass-store/roles success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, role);

        } catch (Exception e) {
            logger.error("POST /glass-store/roles fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "")
    public APIResponse updateRole(@RequestBody RoleInfo roleInfo) {
        long start = System.currentTimeMillis();
        try {
            Role role = roleService.updateRole(roleInfo);
            logger.info("DELETE /glass-store/roles/{roleId} success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, role);

        } catch (Exception e) {
            logger.error("DELETE /glass-store/roles/{roleId} fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{roleId}")
    public APIResponse deleteRole(@PathVariable("roleId") Long roleId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = roleService.deleteRole(roleId);
            logger.info("DELETE /glass-store/roles success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());

        } catch (Exception e) {
            logger.error("DELETE /glass-store/roles fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
