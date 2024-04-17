package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDao extends JpaRepository<City, Long> {
    @Query("SELECT u FROM City u WHERE u.status <> 0")
    List<City> findAllCities();

    @Query("SELECT u FROM City u WHERE u.status <> 0 AND u.cityId = :cityId")
    City findByCityId(@Param("cityId") Long cityId);

    @Query("SELECT u FROM City u WHERE u.status <> 0 AND u.name = :name")
    City findByName(@Param("name") String name);

    @Query("SELECT u FROM City u WHERE u.status <> 0 AND u.name LIKE %:name%")
    List<City> searchByName(@Param("name") String name);
}
