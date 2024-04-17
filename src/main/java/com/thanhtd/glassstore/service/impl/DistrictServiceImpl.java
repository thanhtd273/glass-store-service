package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.DistrictDao;
import com.thanhtd.glassstore.dto.DistrictInfo;
import com.thanhtd.glassstore.model.City;
import com.thanhtd.glassstore.model.District;
import com.thanhtd.glassstore.service.CityService;
import com.thanhtd.glassstore.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private CityService cityService;

    @Override
    public List<District> findAllDistricts() {
        return districtDao.findAllDistricts();
    }

    @Override
    public District findByDistrictId(Long districtId) throws LogicException {
        if (ObjectUtils.isEmpty(districtId))
            throw new LogicException(ErrorCode.ID_NULL);
        return districtDao.findByDistrictId(districtId);
    }

    @Override
    public District findByName(String name) throws LogicException {
        return districtDao.findByName(name);
    }

    @Override
    public List<District> findByCityId(Long cityId) throws LogicException {
        if (ObjectUtils.isEmpty(cityId))
            throw new LogicException(ErrorCode.ID_NULL);
        return districtDao.findByCityId(cityId);
    }

    @Override
    public List<District> searchByName(String name) throws LogicException {
        return districtDao.searchByName(name);
    }

    @Override
    public District createDistrict(DistrictInfo districtInfo) throws LogicException {
        if (ObjectUtils.isEmpty(districtInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (districtInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);
        City city = cityService.findByName(districtInfo.getCityName());
        if (ObjectUtils.isEmpty(city))
            throw new LogicException(ErrorCode.NOT_FOUND_CITY);

        District district = new District();
        district.setCityId(city.getCityId());
        district.setName(districtInfo.getName());
        district.setCode(districtInfo.getCode());
        if (!ObjectUtils.isEmpty(districtInfo.getDescription())) {
            district.setDescription(districtInfo.getDescription());
        }
        district.setStatus(ObjectUtils.isEmpty(districtInfo.getStatus()) ? Status.ACTIVE : districtInfo.getStatus());
        district.setCreateDate(new Date(System.currentTimeMillis()));

        return districtDao.save(district);
    }

    @Override
    public District updateDistrict(DistrictInfo districtInfo) throws LogicException {
        if (ObjectUtils.isEmpty(districtInfo)) throw new LogicException(ErrorCode.DATA_NULL);
        District district = findByDistrictId(districtInfo.getDistrictId());
        if (!ObjectUtils.isEmpty(district.getName())) {
            district.setName(districtInfo.getName());
        }
        if (!ObjectUtils.isEmpty(district.getCode())) {
            district.setCode(districtInfo.getCode());
        }
        if (!ObjectUtils.isEmpty(district.getDescription())) {
            district.setDescription(districtInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(districtInfo.getStatus())) {
            district.setStatus(districtInfo.getStatus());
        }
        if (!ObjectUtils.isEmpty(districtInfo.getCityName())) {
            City city = cityService.findByName(districtInfo.getCityName());
            if (ObjectUtils.isEmpty(city)) throw new LogicException(ErrorCode.NOT_FOUND_CITY);
            else {
                district.setCityId(city.getCityId());
            }
        }
        district.setModifiedDate(new Date(System.currentTimeMillis()));
        return districtDao.save(district);
    }

    @Override
    public ErrorCode deleteDistrict(Long districtId) throws LogicException {
        District district = findByDistrictId(districtId);
        if (ObjectUtils.isEmpty(district))
            return ErrorCode.NOT_FOUND_DISTRICT;

        district.setStatus(Status.DELETED);
        district.setModifiedDate(new Date(System.currentTimeMillis()));
        districtDao.save(district);
        return ErrorCode.OK;
    }
}
