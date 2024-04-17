package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictDao extends JpaRepository<District, Long> {
    @Query("SELECT u FROM District u WHERE u.status <> 0")
    List<District> findAllDistricts();

    @Query("SELECT u FROM District u WHERE u.status <> 0 AND u.districtId = :districtId")
    District findByDistrictId(@Param("districtId") Long districtId);

    @Query("SELECT u FROM District u WHERE u.status <> 0 AND u.name = :name")
    District findByName(@Param("name") String name);

    @Query("SELECT u FROM District u WHERE u.status <> 0 AND u.cityId = :cityId")
    List<District> findByCityId(@Param("cityId") Long cityId);

    @Query("SELECT u FROM District u WHERE u.status <> 0 AND u.name LIKE %:name%")
    List<District> searchByName(@Param("name") String name);

}
