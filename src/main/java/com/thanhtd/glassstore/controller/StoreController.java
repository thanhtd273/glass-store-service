package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.StoreInfo;
import com.thanhtd.glassstore.model.Store;
import com.thanhtd.glassstore.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/stores")
@RequiredArgsConstructor
public class StoreController {
    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    private final StoreService storeService;

    @GetMapping(value = "")
    public APIResponse findAllStores() {
        long start = System.currentTimeMillis();
        try {
            List<Store> storeList = storeService.findAllStores();
            logger.info("GET /glass-store/stores success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, storeList);
        } catch (Exception e) {
            logger.error("GET /glass-store/stores fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{storeId}")
    public APIResponse findByStoreId(@PathVariable("storeId") Long storeId) {
        long start = System.currentTimeMillis();
        try {
            Store store = storeService.findByStoreId(storeId);
            logger.info("GET /glass-store/stores/{} success", storeId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, store);
        } catch (Exception e) {
            logger.error("GET /glass-store/stores/{} fail, error: {}", storeId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse createStore(@RequestBody StoreInfo storeInfo) {
        long start = System.currentTimeMillis();
        try {
            Store store = storeService.createStore(storeInfo);
            logger.info("POST /glass-store/stores success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, store);
        } catch (Exception e) {
            logger.error("POST /glass-store/stores fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "/{storeId}")
    public APIResponse updateStore(@PathVariable("storeId") Long storeId, @RequestBody StoreInfo storeInfo) {
        long start = System.currentTimeMillis();
        try {
            Store store = storeService.updateStore(storeId, storeInfo);
            logger.info("PUT /glass-store/stores success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, store);
        } catch (Exception e) {
            logger.error("PUT /glass-store/stores fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{storeId}")
    public APIResponse deleteStore(@PathVariable("storeId") Long storeId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = storeService.deleteStore(storeId);
            logger.info("DELETE /glass-store/stores success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/stores fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
