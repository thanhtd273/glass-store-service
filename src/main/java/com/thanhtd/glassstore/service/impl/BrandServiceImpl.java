package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.BrandDao;
import com.thanhtd.glassstore.dto.BrandInfo;
import com.thanhtd.glassstore.model.Brand;
import com.thanhtd.glassstore.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandDao brandDao;

    @Override
    public List<Brand> findAllBrands() {
        return brandDao.findAllBrands();
    }

    @Override
    public Brand findByBrandId(Long brandId) throws LogicException {
        if (ObjectUtils.isEmpty(brandId))
            throw new LogicException(ErrorCode.ID_NULL);
        return brandDao.findByBrandId(brandId);
    }

    @Override
    public Brand createBrand(BrandInfo brandInfo) throws LogicException {
        if (ObjectUtils.isEmpty(brandInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (brandInfo.isMissingInfo()) throw new LogicException(ErrorCode.MISSING_DATA);

        Brand brand = new Brand();
        brand.setName(brandInfo.getName());
        if (!ObjectUtils.isEmpty(brandInfo.getDescription())) {
            brand.setDescription(brandInfo.getDescription());
        }
        brand.setStatus(ObjectUtils.isEmpty(brandInfo.getStatus()) ? Status.ACTIVE : brandInfo.getStatus());
        brand.setCreateDate(new Date(System.currentTimeMillis()));
        return brandDao.save(brand);
    }

    @Override
    public Brand updateBrand(Long brandId, BrandInfo brandInfo) throws LogicException {
        Brand brand = findByBrandId(brandId);
        if (ObjectUtils.isEmpty(brand)) throw new LogicException(ErrorCode.NOT_FOUND_DATA);

        if (!ObjectUtils.isEmpty(brandInfo.getName())) {
            brand.setName(brandInfo.getName());
        }
        if (!ObjectUtils.isEmpty(brandInfo.getDescription())) {
            brand.setDescription(brandInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(brandInfo.getStatus())) {
            brand.setStatus(brandInfo.getStatus());
        }
        brand.setModifiedDate(new Date(System.currentTimeMillis()));

        return brandDao.save(brand);
    }

    @Override
    public ErrorCode deleteBrand(Long brandId) throws LogicException {
        Brand brand = findByBrandId(brandId);
        if (ObjectUtils.isEmpty(brand))
            return ErrorCode.NOT_FOUND_DATA;
        brand.setStatus(Status.DELETED);
        brand.setModifiedDate(new Date(System.currentTimeMillis()));
        brandDao.save(brand);
        return ErrorCode.OK;
    }

    @Override
    public Boolean isExistedBrand(Long brandId) {
        Brand brand = brandDao.findByBrandId(brandId);
        return !ObjectUtils.isEmpty(brand);
    }
}
