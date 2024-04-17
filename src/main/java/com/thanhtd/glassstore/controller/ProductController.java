package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.ProductInfo;
import com.thanhtd.glassstore.model.Product;
import com.thanhtd.glassstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/products")
@RequiredArgsConstructor
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @GetMapping(value = "")
    public APIResponse findAllProducts() {
        long start = System.currentTimeMillis();
        try {
            List<Product> productList = productService.findAllProducts();
            logger.info("GET /glass-store/products success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, productList);
        } catch (Exception e) {
            logger.error("GET /glass-store/products fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{productId}")
    public APIResponse findByProductId(@PathVariable("productId") Long productId) {
        long start = System.currentTimeMillis();
        try {
            Product product = productService.findByProductId(productId);
            logger.info("GET /glass-store/products/{} success", productId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, product);
        } catch (Exception e) {
            logger.error("GET /glass-store/products/{} fail, error: {}", productId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createProduct(@RequestBody ProductInfo productInfo) {
        long start = System.currentTimeMillis();
        try {
            Product product = productService.createProduct(productInfo);
            logger.info("POST /glass-store/products success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, product);
        } catch (Exception e) {
            logger.error("POST /glass-store/products fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{productId}")
    public APIResponse updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductInfo productInfo) {
        long start = System.currentTimeMillis();
        try {
            Product product = productService.updateProduct(productId, productInfo);
            logger.info("PUT /glass-store/products success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, product);
        } catch (Exception e) {
            logger.error("PUT /glass-store/products fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{productId}")
    public APIResponse deleteProduct(@PathVariable("productId") Long productId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = productService.deleteProduct(productId);
            logger.info("DELETE /glass-store/products success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/products fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
