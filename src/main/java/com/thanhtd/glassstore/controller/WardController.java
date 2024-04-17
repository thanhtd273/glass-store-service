package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.WardInfo;
import com.thanhtd.glassstore.model.Ward;
import com.thanhtd.glassstore.service.WardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/wards")
@RequiredArgsConstructor
public class WardController {

    private static final Logger logger = LoggerFactory.getLogger(WardController.class);
    private final WardService wardService;

    @GetMapping(value = "")
    public APIResponse findAllWards() {
        long start = System.currentTimeMillis();
        try {
            List<Ward> wardList = wardService.findAllWards();
            logger.info("GET /glass-store/wards success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, wardList);
        } catch (Exception e) {
            logger.error("GET /glass-store/wards fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{wardId}")
    public APIResponse findByWardId(@PathVariable("wardId") Long wardId) {
        long start = System.currentTimeMillis();
        try {
            Ward ward = wardService.findByWardId(wardId);
            logger.info("GET /glass-store/wards/{} success", wardId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, ward);
        } catch (Exception e) {
            logger.error("GET /glass-store/wards/{} fail, error: {}", wardId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByName(@RequestParam("name") String name) {
        long start = System.currentTimeMillis();
        try {
            Ward ward = wardService.findByName(name);
            logger.info("GET /glass-store/wards?name={} success", name);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, ward);
        } catch (Exception e) {
            logger.error("GET /glass-store/wards?name={} fail, error: {}", name, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse create(@RequestBody WardInfo wardInfo) {
        long start = System.currentTimeMillis();
        try {
            Ward ward = wardService.createWard(wardInfo);
            logger.info("POST /glass-store/wards success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, ward);
        } catch (Exception e) {
            logger.error("POST /glass-store/wards fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "")
    public APIResponse update(@RequestBody WardInfo wardInfo) {
        long start = System.currentTimeMillis();
        try {
            Ward ward = wardService.updateWard(wardInfo);
            logger.info("PUT /glass-store/wards success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, ward);
        } catch (Exception e) {
            logger.error("PUT /glass-store/wards fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{wardId}")
    public APIResponse update(@PathVariable("wardId") Long wardId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = wardService.deleteWard(wardId);
            logger.info("DELETE /glass-store/wards success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/wards fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
