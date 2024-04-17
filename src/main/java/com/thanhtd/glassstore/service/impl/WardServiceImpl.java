package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.WardDao;
import com.thanhtd.glassstore.dto.WardInfo;
import com.thanhtd.glassstore.model.City;
import com.thanhtd.glassstore.model.District;
import com.thanhtd.glassstore.model.Ward;
import com.thanhtd.glassstore.service.CityService;
import com.thanhtd.glassstore.service.DistrictService;
import com.thanhtd.glassstore.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class WardServiceImpl implements WardService {
    private final CityService cityService;

    private final DistrictService districtService;

    private final WardDao wardDao;

    @Autowired
    public WardServiceImpl(CityService cityService, DistrictService districtService, WardDao wardDao) {
        this.cityService = cityService;
        this.districtService = districtService;
        this.wardDao = wardDao;
    }
    @Override
    public List<Ward> findAllWards() {
        return wardDao.findAllWards();
    }

    @Override
    public Ward findByWardId(Long wardId) throws LogicException {
        if (ObjectUtils.isEmpty(wardId))
            throw new LogicException(ErrorCode.ID_NULL);
        return wardDao.findByWardId(wardId);
    }

    @Override
    public Ward findByName(String name) throws LogicException {
        return wardDao.findByName(name);
    }

    @Override
    public List<Ward> findByCityId(Long cityId) throws LogicException {
        if (ObjectUtils.isEmpty(cityId))
            throw new LogicException(ErrorCode.ID_NULL);
        return wardDao.findByCityId(cityId);
    }

    @Override
    public List<Ward> findByDistrictId(Long districtId) throws LogicException {
        if (ObjectUtils.isEmpty(districtId))
            throw new LogicException(ErrorCode.ID_NULL);
        return wardDao.findByDistrictId(districtId);
    }

    @Override
    public List<Ward> searchByName(String name) {
        return wardDao.searchByName(name);
    }

    @Override
    public Ward createWard(WardInfo wardInfo) throws LogicException {
        if (ObjectUtils.isEmpty(wardInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (wardInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Ward ward = new Ward();
        ward.setName(wardInfo.getName());
        ward.setCode(wardInfo.getCode());
        if (!ObjectUtils.isEmpty(wardInfo.getDescription())) {
            ward.setDescription(wardInfo.getDescription());
        }
        City city = cityService.findByName(wardInfo.getCityName());
        if (ObjectUtils.isEmpty(city)) {
            throw new LogicException(ErrorCode.NOT_FOUND_CITY);
        } else {
            ward.setCityId(city.getCityId());
        }

        District district = districtService.findByName(wardInfo.getDistrictName());
        if (ObjectUtils.isEmpty(district)) {
            throw new LogicException(ErrorCode.NOT_FOUND_DISTRICT);
        } else {
            ward.setDistrictId(district.getDistrictId());
        }

        ward.setStatus(ObjectUtils.isEmpty(wardInfo.getStatus()) ? Status.ACTIVE : wardInfo.getStatus());
        ward.setCreateDate(new Date(System.currentTimeMillis()));

        return wardDao.save(ward);
    }

    @Override
    public Ward updateWard(WardInfo wardInfo) throws LogicException {
        if (ObjectUtils.isEmpty(wardInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Ward ward = findByWardId(wardInfo.getWardId());
        if (ObjectUtils.isEmpty(ward))
            throw new LogicException(ErrorCode.NOT_FOUND_WARD);

        if (!ObjectUtils.isEmpty(wardInfo.getName())) {
            ward.setName(wardInfo.getName());
        }
        if (!ObjectUtils.isEmpty(wardInfo.getCode())) {
            ward.setCode(wardInfo.getCode());
        }
        if (!ObjectUtils.isEmpty(wardInfo.getDescription())) {
            ward.setDescription(wardInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(wardInfo.getCityName())) {
            City city = cityService.findByName(wardInfo.getCityName());
            if (ObjectUtils.isEmpty(city))
                throw new LogicException(ErrorCode.NOT_FOUND_CITY);
            else {
                ward.setCityId(city.getCityId());
            }
        }
        if (!ObjectUtils.isEmpty(wardInfo.getDistrictName())) {
            District district = districtService.findByName(wardInfo.getDistrictName());
            if (ObjectUtils.isEmpty(district))
                throw new LogicException(ErrorCode.NOT_FOUND_DISTRICT);
            else {
                ward.setDistrictId(district.getDistrictId());
            }
        }
        if (!ObjectUtils.isEmpty(wardInfo.getStatus())) {
            ward.setStatus(wardInfo.getStatus());
        }
        ward.setModifiedDate(new Date(System.currentTimeMillis()));

        return wardDao.save(ward);
    }

    @Override
    public ErrorCode deleteWard(Long wardId) throws LogicException {
        Ward ward = findByWardId(wardId);
        if (ObjectUtils.isEmpty(ward))
            return ErrorCode.NOT_FOUND_WARD;

        ward.setStatus(Status.DELETED);
        ward.setModifiedDate(new Date(System.currentTimeMillis()));
        wardDao.save(ward);

        return ErrorCode.OK;
    }
}
