package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorDao extends JpaRepository<Color, Long> {
    @Query("SELECT u FROM Color u WHERE u.status <> 0")
    List<Color> findAllColors();

    @Query("SELECT u FROM Color u WHERE u.status <> 0 AND u.colorId = :colorId")
    Color findByColorId(@Param("colorId") Long colorId);

    @Query("SELECT u FROM Color u WHERE u.status <> 0 AND u.hexCode = :hexCode")
    Color findByHexCode(@Param("hexCode") String hexCode);
}
