package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.CategoryInfo;
import com.thanhtd.glassstore.model.Category;
import com.thanhtd.glassstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @GetMapping()
    public APIResponse findAllCategories() {
        long start = System.currentTimeMillis();
        try {
            List<Category> categoryList = categoryService.findAllCategories();
            logger.info("GET /glass-store/categories success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, categoryList);
        } catch (Exception e) {
            logger.error("GET /glass-store/categories fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{categoryId}")
    public APIResponse findByCategoryId(@PathVariable("categoryId") Long categoryId) {
        long start = System.currentTimeMillis();
        try {
            Category category = categoryService.findByCategoryId(categoryId);
            logger.info("GET /glass-store/categories/{} success", categoryId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, category);
        } catch (Exception e) {
            logger.error("GET /glass-store/categories/{} fail, error: {}", categoryId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createCategory(@RequestBody CategoryInfo categoryInfo) {
        long start = System.currentTimeMillis();
        try {
            Category category = categoryService.createCategory(categoryInfo);
            logger.info("POST /glass-store/categories success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, category);
        } catch (Exception e) {
            logger.error("POST /glass-store/categories fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{categoryId}")
    public APIResponse updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryInfo categoryInfo) {
        long start = System.currentTimeMillis();
        try {
            Category category = categoryService.updateCategory(categoryId, categoryInfo);
            logger.info("PUT /glass-store/categories success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, category);
        } catch (Exception e) {
            logger.error("PUT /glass-store/categories fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{categoryId}")
    public APIResponse deleteCategory(@PathVariable("categoryId") Long categoryId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = categoryService.deleteCategory(categoryId);
            logger.info("DELETE /glass-store/categories success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/categories fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
