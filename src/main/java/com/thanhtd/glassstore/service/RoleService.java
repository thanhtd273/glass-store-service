package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.RoleInfo;
import com.thanhtd.glassstore.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRoles();

    Role findByRoleId(Long roleId) throws LogicException;

    Role findByName(String name);

    Role createRole(RoleInfo roleInfo) throws LogicException;

    Role updateRole(RoleInfo roleInfo) throws LogicException;

    ErrorCode deleteRole(Long roleId) throws LogicException;

}
