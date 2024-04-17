package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.ActionInfo;
import com.thanhtd.glassstore.model.Action;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface ActionService {
    List<Action> findAllActions() throws LoginException;

    Action findByActionId(Long actionId) throws LogicException;
    Action findByName(String name) throws LogicException;
    Action findByCode(int code) throws LogicException;

    Action createAction(ActionInfo actionInfo) throws LogicException;

    Action updateAction(ActionInfo actionInfo) throws LogicException;

    ErrorCode deleteAction(Long actionId) throws LogicException;
}
