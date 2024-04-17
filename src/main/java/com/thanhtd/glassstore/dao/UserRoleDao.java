package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(Long userId);
    List<UserRole> findByRoleId(Long roleId);
}
