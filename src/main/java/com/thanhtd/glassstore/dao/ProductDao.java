package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    @Query("SELECT u FROM Product u WHERE u.status <> 0")
    List<Product> findAllProducts();

    @Query("SELECT u FROM Product u WHERE u.status <> 0 AND u.productId = :productId")
    Product findByProductId(@Param("productId") Long productId);

    @Query("SELECT u FROM Product u WHERE u.status <> 0 AND u.name = :name")
    Product findByName(@Param("name") String name);

    @Query("SELECT u FROM Product u WHERE u.status <> 0 AND u.name LIKE %:name%")
    List<Product> searchByName(@Param("name") String name);
}
