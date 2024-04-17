package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardDao extends JpaRepository<Ward, Long> {
    @Query("SELECT u FROM Ward u WHERE u.status <> 0")
    List<Ward> findAllWards();

    @Query("SELECT u FROM Ward u WHERE u.status <> 0 AND u.wardId = :wardId")
    Ward findByWardId(@Param("wardId") Long wardId);

    @Query("SELECT u FROM Ward u WHERE u.status <> 0 AND u.name = :name")
    Ward findByName(@Param("name") String name);

    @Query("SELECT u FROM Ward u WHERE u.status <> 0 AND u.cityId = :cityId")
    List<Ward> findByCityId(@Param("cityId") Long cityId);

    @Query("SELECT u FROM Ward u WHERE u.status <> 0 AND u.districtId = :districtId")
    List<Ward> findByDistrictId(@Param("districtId") Long districtId);

    @Query("SELECT u FROM Ward u WHERE u.status <> 0 AND u.name LIKE %:name%")
    List<Ward> searchByName(@Param("name") String name);
}
