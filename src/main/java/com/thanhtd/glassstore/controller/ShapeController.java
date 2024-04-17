package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.ShapeInfo;
import com.thanhtd.glassstore.model.Shape;
import com.thanhtd.glassstore.service.ShapeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/shapes")
@RequiredArgsConstructor
public class ShapeController {
    private static final Logger logger = LoggerFactory.getLogger(ShapeController.class);

    private final ShapeService shapeService;

    @GetMapping(value = "")
    public APIResponse findAllShapes() {
        long start = System.currentTimeMillis();
        try {
            List<Shape> shapeList = shapeService.findAllShapes();
            logger.info("GET /glass-store/shapes success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, shapeList);
        } catch (Exception e) {
            logger.error("GET /glass-store/shapes fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{shapeId}")
    public APIResponse findByShapeId(@PathVariable("shapeId") Long shapeId) {
        long start = System.currentTimeMillis();
        try {
            Shape shape = shapeService.findByShapeId(shapeId);
            logger.info("GET /glass-store/shapes/{} success", shapeId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, shape);
        } catch (Exception e) {
            logger.error("GET /glass-store/shapes/{} fail, error: {}", shapeId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createShape(@RequestBody ShapeInfo shapeInfo) {
        long start = System.currentTimeMillis();
        try {
            Shape shape = shapeService.createShape(shapeInfo);
            logger.info("POST /glass-store/shapes success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, shape);
        } catch (Exception e) {
            logger.error("POST /glass-store/shapes fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{shapeId}")
    public APIResponse updateShape(@PathVariable("shapeId") Long shapeId, @RequestBody ShapeInfo shapeInfo) {
        long start = System.currentTimeMillis();
        try {
            Shape shape = shapeService.updateShape(shapeId, shapeInfo);
            logger.info("PUT /glass-store/shapes success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, shape);
        } catch (Exception e) {
            logger.error("PUT /glass-store/shapes fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{shapeId}")
    public APIResponse deleteShape(@PathVariable("shapeId") Long shapeId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = shapeService.deleteShape(shapeId);
            logger.info("DELETE /glass-store/shapes success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/shapes fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
