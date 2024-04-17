package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.PermissionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionActionDao extends JpaRepository<PermissionAction, Long> {
    List<PermissionAction> findByPermissionId(Long permissionId);

    List<PermissionAction> findByActionId(Long actionId);
}
