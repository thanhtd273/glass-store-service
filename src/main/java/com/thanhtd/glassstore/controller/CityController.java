package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.CityInfo;
import com.thanhtd.glassstore.model.City;
import com.thanhtd.glassstore.service.CityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/cities")
@RequiredArgsConstructor
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    @GetMapping(value = "")
    public APIResponse findAllCities() {
        long start = System.currentTimeMillis();
        try {
            List<City> cityList = cityService.findAllCities();
            logger.info("GET /glass-store/cities success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, cityList);
        } catch (Exception e) {
            logger.error("GET /glass-store/cities fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{cityId}")
    public APIResponse findByCityId(@PathVariable("cityId") Long cityId) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.findByCityId(cityId);
            logger.info("GET /glass-store/cities/{} success", cityId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("GET /glass-store/cities/{} fail, error: {}", cityId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByName(@RequestParam("name") String name) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.findByName(name);
            logger.info("GET /glass-store/cities?name={} success", name);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("GET /glass-store/cities?name={} fail, error: {}", name, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping(value = "")
    public APIResponse create(@RequestBody CityInfo cityInfo) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.createCity(cityInfo);
            logger.info("POST /glass-store/cities success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("POST /glass-store/cities fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping(value = "")
    public APIResponse update(@RequestBody CityInfo cityInfo) {
        long start = System.currentTimeMillis();
        try {
            City city = cityService.updateCity(cityInfo);
            logger.info("PUT /glass-store/cities success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, city);
        } catch (Exception e) {
            logger.error("PUT /glass-store/cities fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{cityId}")
    public APIResponse update(@PathVariable("cityId") Long cityId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = cityService.deleteCity(cityId);
            logger.info("DELETE /glass-store/cities success");
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/cities fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
