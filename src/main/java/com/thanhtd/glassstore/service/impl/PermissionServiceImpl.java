package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.PermissionDao;
import com.thanhtd.glassstore.dto.PermissionInfo;
import com.thanhtd.glassstore.model.Permission;
import com.thanhtd.glassstore.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionDao permissionDao;

    @Autowired
    public PermissionServiceImpl(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }
    @Override
    public List<Permission> findAllPermission() {
        return permissionDao.findAllPermissions();
    }

    @Override
    public Permission findByPermissionId(Long permissionId) throws LogicException {
        if (ObjectUtils.isEmpty(permissionId)) throw new LogicException(ErrorCode.ID_NULL);

        return permissionDao.findByPermissionId(permissionId);
    }

    @Override
    public Permission findByName(String name) throws LogicException {
        if (ObjectUtils.isEmpty(name)) throw new LogicException(ErrorCode.MISSING_DATA);

        return permissionDao.findByName(name);
    }

    @Override
    public Permission createPermission(PermissionInfo permissionInfo) throws LogicException {
        if (ObjectUtils.isEmpty(permissionInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (permissionInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Permission permission = new Permission();
        permission.setName(permissionInfo.getName());
        if (!ObjectUtils.isEmpty(permissionInfo.getDescription())) {
            permission.setDescription(permission.getDescription());
        }
        permission.setStatus(ObjectUtils.isEmpty(permissionInfo.getStatus()) ? Status.ACTIVE : permissionInfo.getStatus());
        permission.setCreateDate(new Date());

        permission = permissionDao.save(permission);
        return permission;
    }

    @Override
    public Permission updatePermission(PermissionInfo permissionInfo) throws LogicException {
        if (ObjectUtils.isEmpty(permissionInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Permission permission = findByPermissionId(permissionInfo.getPermissionId());
        if (!ObjectUtils.isEmpty(permissionInfo.getName())) {
            permission.setName(permissionInfo.getName());
        }
        if (!ObjectUtils.isEmpty(permissionInfo.getDescription())) {
            permission.setDescription(permissionInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(permissionInfo.getStatus())) {
            permission.setStatus(permissionInfo.getStatus());
        }
        permission.setModifiedDate(new Date());

        permission = permissionDao.save(permission);
        return permission;
    }

    @Override
    public ErrorCode deletePermission(Long permissionId) throws LogicException {
        Permission permission = findByPermissionId(permissionId);
        if (ObjectUtils.isEmpty(permission))
            return ErrorCode.NOT_FOUND_DATA;

        permission.setStatus(Status.DELETED);
        permission.setModifiedDate(new Date());

        permissionDao.save(permission);
        return ErrorCode.OK;
    }
}
