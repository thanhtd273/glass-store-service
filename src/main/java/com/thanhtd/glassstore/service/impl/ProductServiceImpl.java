package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.ProductDao;
import com.thanhtd.glassstore.dto.ProductInfo;
import com.thanhtd.glassstore.dto.ProductSearchCriteria;
import com.thanhtd.glassstore.model.Brand;
import com.thanhtd.glassstore.model.Category;
import com.thanhtd.glassstore.model.Product;
import com.thanhtd.glassstore.service.BrandService;
import com.thanhtd.glassstore.service.CategoryService;
import com.thanhtd.glassstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final BrandService brandService;

    private final CategoryService categoryService;

    @Override
    public Product createProduct(ProductInfo productInfo) throws LogicException {
        if (ObjectUtils.isEmpty(productInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (productInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Product product = new Product();
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());
        product.setMaterial(productInfo.getMaterial());

        Long brandId = productInfo.getBrandId();
        Brand brand = brandService.findByBrandId(brandId);
        if (ObjectUtils.isEmpty(brand))
            throw new LogicException(ErrorCode.NOT_FOUND_BRAND);
        product.setBrandId(brand.getBrandId());

        Long categoryId = productInfo.getCategoryId();
        Category category = categoryService.findByCategoryId(categoryId);
        if (ObjectUtils.isEmpty(category))
            throw new LogicException(ErrorCode.NOT_FOUND_CATEGORY);
        product.setCategoryId(category.getCategoryId());

        if (!ObjectUtils.isEmpty(productInfo.getDescription())) {
            product.setDescription(productInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(productInfo.getDiscount())) {
            product.setDiscount(productInfo.getDiscount());
        }
        product.setStatus(ObjectUtils.isEmpty(productInfo.getStatus()) ? Status.ACTIVE : productInfo.getStatus());
        product.setCreateDate(new Date(System.currentTimeMillis()));

        return productDao.save(product);
    }

    @Override
    public Product updateProduct(Long productId, ProductInfo productInfo) throws LogicException {
        if (ObjectUtils.isEmpty(productInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Product product = findByProductId(productId);
        if (ObjectUtils.isEmpty(product))
            throw new LogicException(ErrorCode.NOT_FOUND_PRODUCT);

        if (!ObjectUtils.isEmpty(productInfo.getName())) {
            product.setName(productInfo.getName());
        }
        if (!ObjectUtils.isEmpty(productInfo.getDescription())) {
            product.setDescription(productInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(productInfo.getPrice())) {
            product.setPrice(productInfo.getPrice());
        }
        if (!ObjectUtils.isEmpty(productInfo.getDiscount())) {
            product.setDiscount(productInfo.getDiscount());
        }
        if (!ObjectUtils.isEmpty(productInfo.getMaterial())) {
            product.setMaterial(productInfo.getMaterial());
        }
        Long brandId = productInfo.getBrandId();
        if (!ObjectUtils.isEmpty(brandId) && brandService.isExistedBrand(brandId)) {
            product.setBrandId(brandId);
        }
        Long categoryId = productInfo.getCategoryId();
        if (!ObjectUtils.isEmpty(product.getCategoryId()) && categoryService.isExistedCategory(categoryId)) {
            product.setCategoryId(categoryId);
        }
        if (!ObjectUtils.isEmpty(productInfo.getStatus())) {
            product.setStatus(productInfo.getStatus());
        }
        product.setModifiedDate(new Date(System.currentTimeMillis()));
        return productDao.save(product);
    }

    @Override
    public ErrorCode deleteProduct(Long productId) throws LogicException {
        Product product = findByProductId(productId);
        if (ObjectUtils.isEmpty(product)) return ErrorCode.NOT_FOUND_PRODUCT;

        product.setStatus(Status.DELETED);
        product.setModifiedDate(new Date(System.currentTimeMillis()));
        productDao.save(product);
        return ErrorCode.OK;
    }

    @Override
    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    @Override
    public Product findByProductId(Long productId) throws LogicException {
        if (ObjectUtils.isEmpty(productId)) throw new LogicException(ErrorCode.ID_NULL);
        return productDao.findByProductId(productId);
    }

    @Override
    public List<Product> search(ProductSearchCriteria searchCriteria) throws LogicException {
        return List.of();
    }
}
