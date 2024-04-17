package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.BrandInfo;
import com.thanhtd.glassstore.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> findAllBrands();
    Brand findByBrandId(Long brandId) throws LogicException;
    Brand createBrand(BrandInfo brandInfo) throws LogicException;
    Brand updateBrand(Long brandId, BrandInfo brandInfo) throws LogicException;
    ErrorCode deleteBrand(Long brandId) throws LogicException;

    Boolean isExistedBrand(Long brandId);
}
