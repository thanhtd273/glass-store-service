package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureDao extends JpaRepository<Feature, Long> {
    @Query("SELECT u FROM Feature u WHERE u.status <> 0")
    List<Feature> findAllFeatures();

    @Query("SELECT u FROM Feature u WHERE u.status <> 0 AND u.featureId = :featureId")
    Feature findByFeatureId(@Param("featureId") Long featureId);
}
