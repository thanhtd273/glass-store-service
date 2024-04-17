package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandDao extends JpaRepository<Brand, Long> {
    @Query("SELECT u FROM Brand u WHERE u.status <> 0")
    List<Brand> findAllBrands();

    @Query("SELECT u FROM Brand u WHERE u.status <> 0 AND u.brandId = :brandId")
    Brand findByBrandId(@Param("brandId") Long brandId);
}
