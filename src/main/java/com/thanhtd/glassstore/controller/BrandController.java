package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.BrandInfo;
import com.thanhtd.glassstore.model.Brand;
import com.thanhtd.glassstore.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/brands")
@RequiredArgsConstructor
public class BrandController {
    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    private final BrandService brandService;

    @GetMapping()
    public APIResponse findAllBrands() {
        long start = System.currentTimeMillis();
        try {
            List<Brand> brandList = brandService.findAllBrands();
            logger.info("GET /glass-store/brands success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, brandList);
        } catch (Exception e) {
            logger.error("GET /glass-store/brands fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{brandId}")
    public APIResponse findByBrandId(@PathVariable("brandId") Long brandId) {
        long start = System.currentTimeMillis();
        try {
            Brand brand = brandService.findByBrandId(brandId);
            logger.info("GET /glass-store/brands/{} success", brandId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, brand);
        } catch (Exception e) {
            logger.error("GET /glass-store/brands/{} fail, error: {}", brandId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createBrand(@RequestBody BrandInfo brandInfo) {
        long start = System.currentTimeMillis();
        try {
            Brand brand = brandService.createBrand(brandInfo);
            logger.info("POST /glass-store/brands success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, brand);
        } catch (Exception e) {
            logger.error("POST /glass-store/brands fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{brandId}")
    public APIResponse updateBrand(@PathVariable("brandId") Long brandId, @RequestBody BrandInfo brandInfo) {
        long start = System.currentTimeMillis();
        try {
            Brand brand = brandService.updateBrand(brandId, brandInfo);
            logger.info("PUT /glass-store/brands success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, brand);
        } catch (Exception e) {
            logger.error("PUT /glass-store/brands fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{brandId}")
    public APIResponse deleteBrand(@PathVariable("brandId") Long brandId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = brandService.deleteBrand(brandId);
            logger.info("DELETE /glass-store/brands success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/brands fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
