package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.ColorInfo;
import com.thanhtd.glassstore.model.Color;
import com.thanhtd.glassstore.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/colors")
@RequiredArgsConstructor
public class ColorController {
    private static final Logger logger = LoggerFactory.getLogger(ColorController.class);
    
    private final ColorService colorService;

    @GetMapping(value = "")
    public APIResponse findAllColors() {
        long start = System.currentTimeMillis();
        try {
            List<Color> colorList = colorService.findAllColors();
            logger.info("GET /glass-store/colors success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, colorList);
        } catch (Exception e) {
            logger.error("GET /glass-store/colors fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{colorId}")
    public APIResponse findByColorId(@PathVariable("colorId") Long colorId) {
        long start = System.currentTimeMillis();
        try {
            Color color = colorService.findByColorId(colorId);
            logger.info("GET /glass-store/colors/{} success", colorId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, color);
        } catch (Exception e) {
            logger.error("GET /glass-store/colors/{} fail, error: {}", colorId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse create(@RequestBody ColorInfo colorInfo) {
        long start = System.currentTimeMillis();
        try {
            Color color = colorService.createColor(colorInfo);
            logger.info("POST /glass-store/colors success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, color);
        } catch (Exception e) {
            logger.error("POST /glass-store/colors fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{colorId}")
    public APIResponse update(@PathVariable("colorId") Long colorId, @RequestBody ColorInfo colorInfo) {
        long start = System.currentTimeMillis();
        try {
            Color color = colorService.updateColor(colorId, colorInfo);
            logger.info("PUT /glass-store/colors success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, color);
        } catch (Exception e) {
            logger.error("PUT /glass-store/colors fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{colorId}")
    public APIResponse update(@PathVariable("colorId") Long colorId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = colorService.deleteColor(colorId);
            logger.info("DELETE /glass-store/colors success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/colors fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
