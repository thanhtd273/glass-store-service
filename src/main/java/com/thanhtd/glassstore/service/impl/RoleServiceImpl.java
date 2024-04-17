package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.RoleDao;
import com.thanhtd.glassstore.dto.RoleInfo;
import com.thanhtd.glassstore.model.Role;
import com.thanhtd.glassstore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Override
    public Role findByRoleId(Long roleId) throws LogicException {
        if (ObjectUtils.isEmpty(roleId))
            throw new LogicException(ErrorCode.ID_NULL);
        return roleDao.findByRoleId(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public Role createRole(RoleInfo roleInfo) throws LogicException {
        if (ObjectUtils.isEmpty(roleInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (ObjectUtils.isEmpty(roleInfo.getName()))
            throw new LogicException(ErrorCode.MISSING_DATA);

        Role role = new Role();
        role.setName(roleInfo.getName());
        if (!ObjectUtils.isEmpty(roleInfo.getDescription())) {
            role.setDescription(roleInfo.getDescription());
        }
        role.setStatus(ObjectUtils.isEmpty(roleInfo.getStatus()) ? Status.ACTIVE : roleInfo.getStatus());
        role.setCreateDate(new Date(System.currentTimeMillis()));
        return roleDao.save(role);
    }

    @Override
    public Role updateRole(RoleInfo roleInfo) throws LogicException {
        if (ObjectUtils.isEmpty(roleInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Role role = findByRoleId(roleInfo.getRoleId());
        if (ObjectUtils.isEmpty(role))
            throw new LogicException(ErrorCode.NOT_FOUND_ROLE);

        if (!ObjectUtils.isEmpty(roleInfo.getName())) {
            role.setName(roleInfo.getName());
        }
        if (!ObjectUtils.isEmpty(roleInfo.getDescription())) {
            role.setDescription(roleInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(roleInfo.getStatus())) {
            role.setStatus(roleInfo.getStatus());
        }
        role.setModifiedDate(new Date(System.currentTimeMillis()));
        return roleDao.save(role);
    }

    @Override
    public ErrorCode deleteRole(Long roleId) throws LogicException {
        Role role = findByRoleId(roleId);
        if (ObjectUtils.isEmpty(role))
            return ErrorCode.NOT_FOUND_ROLE;

        role.setStatus(Status.DELETED);
        role.setModifiedDate(new Date(System.currentTimeMillis()));
        roleDao.save(role);

        return ErrorCode.OK;
    }
}
