package com.thanhtd.glassstore.controller;

import com.thanhtd.glassstore.core.APIResponse;
import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.GlobalConstant;
import com.thanhtd.glassstore.core.exception.ExceptionHandler;
import com.thanhtd.glassstore.dto.ActionInfo;
import com.thanhtd.glassstore.model.Action;
import com.thanhtd.glassstore.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/glass-store/actions")
@RequiredArgsConstructor
public class ActionController {
    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);

    private final ActionService actionService;

    @GetMapping(value = "")
    public APIResponse findAllActions() {
        long start = System.currentTimeMillis();
        try {
            List<Action> actions = actionService.findAllActions();
            logger.info("GET /glass-store/actions success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, actions);
        } catch (Exception e) {
            logger.error("GET /glass-store/actions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/{actionId}")
    public APIResponse findByActionId(@PathVariable("actionId") Long actionId) {
        long start = System.currentTimeMillis();
        try {
            Action action = actionService.findByActionId(actionId);
            logger.info("GET /glass-store/actions/{} success", actionId);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, action);
        } catch (Exception e) {
            logger.error("GET /glass-store/actions/{} fail, error: {}", actionId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @GetMapping(value = "/search")
    public APIResponse findByName(@RequestParam("name") String name) {
        long start = System.currentTimeMillis();
        try {
            Action action = actionService.findByName(name);
            logger.info("GET /glass-store/actions?name={} success", name);
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, action);
        } catch (Exception e) {
            logger.error("GET /glass-store/actions?name={} fail, error: {}", name, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PostMapping()
    public APIResponse create(@RequestBody ActionInfo actionInfo) {
        long start = System.currentTimeMillis();
        try {
            Action action = actionService.createAction(actionInfo);
            logger.info("POST /glass-store/actions success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, action);
        } catch (Exception e) {
            logger.error("POST /glass-store/actions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @PutMapping()
    public APIResponse update(@RequestBody ActionInfo actionInfo) {
        long start = System.currentTimeMillis();
        try {
            Action action = actionService.updateAction(actionInfo);
            logger.info("PUT /glass-store/actions success");
            return new APIResponse(GlobalConstant.SUCCESS_STATUS, GlobalConstant.SUCCESS, "", System.currentTimeMillis() - start, action);
        } catch (Exception e) {
            logger.error("PUT /glass-store/actions fail, error: {}", e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }

    @DeleteMapping(value = "/{actionId}")
    public APIResponse delete(@PathVariable("actionId") Long actionId) {
        long start = System.currentTimeMillis();
        try {
            ErrorCode errorCode = actionService.deleteAction(actionId);
            logger.info("DELETE /glass-store/actions/{} success", actionId);
            return new APIResponse(errorCode, "", System.currentTimeMillis() - start, errorCode.getMessage());
        } catch (Exception e) {
            logger.error("DELETE /glass-store/actions/{} fail, error: {}", actionId, e.getMessage());
            return ExceptionHandler.handleException(e, start);
        }
    }
}
