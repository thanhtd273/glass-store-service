package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends JpaRepository<Permission, Long> {
    @Query("SELECT u FROM Permission u WHERE u.status <> 0")
    List<Permission> findAllPermissions();

    @Query("SELECT u FROM Permission u WHERE u.status <> 0 AND u.permissionId = :permissionId")
    Permission findByPermissionId(@Param("permissionId") Long permissionId);

    @Query("SELECT u FROM Permission u WHERE u.status <> 0 AND u.name = :name")
    Permission findByName(@Param("name") String name);
}
