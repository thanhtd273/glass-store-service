package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.ActionDao;
import com.thanhtd.glassstore.dto.ActionInfo;
import com.thanhtd.glassstore.model.Action;
import com.thanhtd.glassstore.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;

    @Autowired
    public ActionServiceImpl(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @Override
    public List<Action> findAllActions() throws LoginException {
        return actionDao.findAllActions();
    }

    @Override
    public Action findByActionId(Long actionId) throws LogicException {
        return actionDao.findByActionId(actionId);
    }

    @Override
    public Action findByCode(int code) throws LogicException {
        return actionDao.findByCode(code);
    }

    @Override
    public Action findByName(String name) throws LogicException {
        return actionDao.findByName(name);
    }

    @Override
    public Action createAction(ActionInfo actionInfo) throws LogicException {
        if (ObjectUtils.isEmpty(actionInfo)) throw new LogicException(ErrorCode.DATA_NULL);
        if (actionInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Action action = new Action();
        action.setName(actionInfo.getName());
        action.setCode(action.getCode());
        action.setCreateDate(new Date());
        action.setStatus(Status.ACTIVE);

        if (!ObjectUtils.isEmpty(actionInfo.getDescription())) {
            action.setDescription(actionInfo.getDescription());
        }

        action = actionDao.save(action);

        return action;
    }

    @Override
    public Action updateAction(ActionInfo actionInfo) throws LogicException {
        if (ObjectUtils.isEmpty(actionInfo))
            throw new LogicException(ErrorCode.DATA_NULL);

        Action action = findByActionId(actionInfo.getActionId());
        if (ObjectUtils.isEmpty(action))
            throw new LogicException(ErrorCode.NOT_FOUND_DATA);

        if (!ObjectUtils.isEmpty(actionInfo.getActionCode())) {
            action.setCode(actionInfo.getActionCode());
        }
        if (!ObjectUtils.isEmpty(actionInfo.getName())) {
            action.setName(actionInfo.getName());
        }
        if (!ObjectUtils.isEmpty(actionInfo.getDescription())) {
            action.setDescription(actionInfo.getDescription());
        }
        action.setModifiedDate(new Date());

        action = actionDao.save(action);
        return action;
    }

    @Override
    public ErrorCode deleteAction(Long actionId) throws LogicException {
        Action action = findByActionId(actionId);
        if (ObjectUtils.isEmpty(action))
            return ErrorCode.NOT_FOUND_DATA;

        action.setStatus(Status.DELETED);
        action.setModifiedDate(new Date());
        actionDao.save(action);

        return ErrorCode.OK;
    }
}
