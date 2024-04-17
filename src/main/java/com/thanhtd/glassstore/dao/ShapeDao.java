package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShapeDao extends JpaRepository<Shape, Long> {
    @Query("SELECT u FROM Shape u WHERE u.status <> 0")
    List<Shape> findAllShapes();

    @Query("SELECT u FROM Shape u WHERE u.status <> 0 AND u.shapeId = :shapeId")
    Shape findByShapeId(@Param("shapeId") Long shapeId);

    @Query("SELECT u FROM Shape u WHERE u.status <> 0 AND u.name = :name")
    Shape findByName(@Param("name") String name);
}
