package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.DistrictInfo;
import com.thanhtd.glassstore.model.District;
import com.thanhtd.glassstore.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/districts")
@RequiredArgsConstructor
public class DistrictController {

    private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

    private final DistrictService districtService;

    @GetMapping(value = "")
    public APIResponse findAllDistricts() {
        long start = System.currentTimeMillis();
        try {
            List<District> districtList = districtService.findAllDistricts();
            logger.info("GET /glass-store/districts success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, districtList);
        } catch (Exception e) {
            logger.error("GET /glass-store/districts fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{districtId}")
    public APIResponse findByDistrictId(@PathVariable("districtId") Long districtId) {
        long start = System.currentTimeMillis();
        try {
            District district = districtService.findByDistrictId(districtId);
            logger.info("GET /glass-store/districts/{} success", districtId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, district);
        } catch (Exception e) {
            logger.error("GET /glass-store/districts/{} fail, error: {}", districtId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByName(@RequestParam("name") String name) {
        long start = System.currentTimeMillis();
        try {
            District district = districtService.findByName(name);
            logger.info("GET /glass-store/districts?name={} success", name);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, district);
        } catch (Exception e) {
            logger.error("GET /glass-store/districts?name={} fail, error: {}", name, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse create(@RequestBody DistrictInfo districtInfo) {
        long start = System.currentTimeMillis();
        try {
            District district = districtService.createDistrict(districtInfo);
            logger.info("POST /glass-store/districts success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, district);
        } catch (Exception e) {
            logger.error("POST /glass-store/districts fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "")
    public APIResponse update(@RequestBody DistrictInfo districtInfo) {
        long start = System.currentTimeMillis();
        try {
            District district = districtService.updateDistrict(districtInfo);
            logger.info("PUT /glass-store/districts success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, district);
        } catch (Exception e) {
            logger.error("PUT /glass-store/districts fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{districtId}")
    public APIResponse update(@PathVariable("districtId") Long districtId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = districtService.deleteDistrict(districtId);
            logger.info("DELETE /glass-store/districts success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/districts fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
