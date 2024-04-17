package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDao extends JpaRepository<Image, Long> {
    @Query("SELECT u FROM Image u WHERE u.status <> 0")
    List<Image> findAllImages();

    @Query("SELECT u FROM Image u WHERE u.status <> 0 AND u.imageId = :imageId")
    Image findByImageId(@Param("imageId") Long imageId);

}
