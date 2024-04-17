package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionDao extends JpaRepository<Action, Long> {
    @Query("SELECT u FROM Action u WHERE u.status <> 0")
    List<Action> findAllActions();

    @Query("SELECT u FROM Action u WHERE u.status <> 0 AND u.actionId = :actionId")
    Action findByActionId(@Param("actionId") Long actionId);

    @Query("SELECT u FROM Action u WHERE u.status <> 0 AND u.code = :code")
    Action findByCode(@Param("code") Integer code);

    @Query("SELECT u FROM Action u WHERE u.status <> 0 AND u.name = :name")
    Action findByName(@Param("name") String name);
}
