package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionDao extends JpaRepository<RolePermission, Long> {
    List<RolePermission> findByRoleId(Long roleId);
    List<RolePermission> findByPermissionId(Long permissionId);
}
