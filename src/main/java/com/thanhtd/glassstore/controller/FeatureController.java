package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.FeatureInfo;
import com.thanhtd.glassstore.model.Feature;
import com.thanhtd.glassstore.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/features")
@RequiredArgsConstructor
public class FeatureController {
    private static final Logger logger = LoggerFactory.getLogger(FeatureController.class);

    private final FeatureService featureService;

    @GetMapping(value = "")
    public APIResponse findAllFeatures() {
        long start = System.currentTimeMillis();
        try {
            List<Feature> featureList = featureService.findAllFeatures();
            logger.info("GET /glass-store/features success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, featureList);
        } catch (Exception e) {
            logger.error("GET /glass-store/features fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{featureId}")
    public APIResponse findByFeatureId(@PathVariable("featureId") Long featureId) {
        long start = System.currentTimeMillis();
        try {
            Feature feature = featureService.findByFeatureId(featureId);
            logger.info("GET /glass-store/features/{} success", featureId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, feature);
        } catch (Exception e) {
            logger.error("GET /glass-store/features/{} fail, error: {}", featureId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createFeature(@RequestBody FeatureInfo featureInfo) {
        long start = System.currentTimeMillis();
        try {
            Feature feature = featureService.createFeature(featureInfo);
            logger.info("POST /glass-store/features success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, feature);
        } catch (Exception e) {
            logger.error("POST /glass-store/features fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{featureId}")
    public APIResponse updateFeature(@PathVariable("featureId") Long featureId, @RequestBody FeatureInfo featureInfo) {
        long start = System.currentTimeMillis();
        try {
            Feature feature = featureService.updateFeature(featureId, featureInfo);
            logger.info("PUT /glass-store/features success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, feature);
        } catch (Exception e) {
            logger.error("PUT /glass-store/features fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{featureId}")
    public APIResponse deleteFeature(@PathVariable("featureId") Long featureId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = featureService.deleteFeature(featureId);
            logger.info("DELETE /glass-store/features success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/features fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
