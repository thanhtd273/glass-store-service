package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.PermissionInfo;
import com.thanhtd.glassstore.model.Permission;
import com.thanhtd.glassstore.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionService permissionService;

    @GetMapping(value = "")
    public APIResponse findAllPermissions() {
        long start = System.currentTimeMillis();
        try {
            List<Permission> permissionList = permissionService.findAllPermission();
            logger.info("GET /glass-store/permissions success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, permissionList);
        } catch (Exception e) {
            logger.error("GET /glass-store/permissions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{permissionId}")
    public APIResponse findByPermissionId(@PathVariable("permissionId") Long permissionId) {
        long start = System.currentTimeMillis();
        try {
            Permission permission = permissionService.findByPermissionId(permissionId);
            logger.info("GET /glass-store/permissions/{} success", permissionId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, permission);
        } catch (Exception e) {
            logger.error("GET /glass-store/permissions/{} fail, error: {}", permissionId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByPermissionName(@RequestParam("name") String name) {
        long start = System.currentTimeMillis();
        try {
            Permission permission = permissionService.findByName(name);
            logger.info("GET /glass-store/permissions?name={} success", name);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, permission);
        } catch (Exception e) {
            logger.error("GET /glass-store/permissions?name={} fail, error: {}", name, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createPermission(@RequestBody PermissionInfo permissionInfo) {
        long start = System.currentTimeMillis();
        try {
            Permission permission = permissionService.createPermission(permissionInfo);
            logger.info("POST /glass-store/permissions success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, permission);
        } catch (Exception e) {
            logger.error("POST /glass-store/permissions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "")
    public APIResponse updatePermission(@RequestBody PermissionInfo permissionInfo) {
        long start = System.currentTimeMillis();
        try {
            Permission permission = permissionService.updatePermission(permissionInfo);
            logger.info("PUT /glass-store/permissions success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, permission);
        } catch (Exception e) {
            logger.error("PUT /glass-store/permissions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{permissionId}")
    public APIResponse deletePermission(@PathVariable("permissionId") Long permissionId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = permissionService.deletePermission(permissionId);
            logger.info("DELETE /glass-store/permissions success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/permissions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
