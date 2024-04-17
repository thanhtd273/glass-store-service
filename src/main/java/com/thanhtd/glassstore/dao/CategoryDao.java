package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
    @Query("SELECT u FROM Category u WHERE u.status <> 0")
    List<Category> findAllCategories();

    @Query("SELECT u FROM Category u WHERE u.status <> 0 AND u.categoryId = :categoryId")
    Category findByCategoryId(@Param("categoryId") Long categoryId);
}
