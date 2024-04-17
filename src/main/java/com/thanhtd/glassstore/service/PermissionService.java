package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.PermissionInfo;
import com.thanhtd.glassstore.model.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAllPermission();

    Permission findByPermissionId(Long permissionId) throws LogicException;

    Permission findByName(String name) throws LogicException;

    Permission createPermission(PermissionInfo permissionInfo) throws LogicException;

    Permission updatePermission(PermissionInfo permissionInfo) throws LogicException;

    ErrorCode deletePermission(Long permissionId) throws LogicException;
}
