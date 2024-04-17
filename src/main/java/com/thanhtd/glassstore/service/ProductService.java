package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.ProductInfo;
import com.thanhtd.glassstore.dto.ProductSearchCriteria;
import com.thanhtd.glassstore.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductInfo productInfo) throws LogicException;
    Product updateProduct(Long productId, ProductInfo productInfo) throws LogicException;
    ErrorCode deleteProduct(Long productId) throws LogicException;
    List<Product> findAllProducts();
    Product findByProductId(Long productId) throws LogicException;
    List<Product> search(ProductSearchCriteria searchCriteria) throws LogicException;

}
