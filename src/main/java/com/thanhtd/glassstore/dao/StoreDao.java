package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDao extends JpaRepository<Store, Long> {
    @Query("SELECT u FROM Store u WHERE u.status <> 0")
    List<Store> findAllStores();

    @Query("SELECT u FROM Store u WHERE u.status <> 0 AND u.storeId = :storeId")
    Store findByStoreId(@Param("storeId") Long storeId);
}
