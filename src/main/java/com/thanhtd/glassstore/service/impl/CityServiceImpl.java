package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.CityDao;
import com.thanhtd.glassstore.dto.CityInfo;
import com.thanhtd.glassstore.model.City;
import com.thanhtd.glassstore.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityDao cityDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }
    @Override
    public List<City> findAllCities() {
        return cityDao.findAllCities();
    }

    @Override
    public City findByCityId(Long cityId) throws LogicException {
        if (ObjectUtils.isEmpty(cityId)) throw new LogicException(ErrorCode.ID_NULL);

        return cityDao.findByCityId(cityId);
    }

    @Override
    public City findByName(String name) throws LogicException {
        if (ObjectUtils.isEmpty(name))
            throw new LogicException(ErrorCode.MISSING_DATA);
        return cityDao.findByName(name);
    }

    @Override
    public List<City> searchByName(String name) {
        return cityDao.searchByName(name);
    }

    @Override
    public City createCity(CityInfo cityInfo) throws LogicException {
        if (ObjectUtils.isEmpty(cityInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (cityInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        City city = new City();
        city.setName(cityInfo.getName());
        if (!ObjectUtils.isEmpty(cityInfo.getDescription())) {
            city.setDescription(cityInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(cityInfo.getCode())) {
            city.setCode(cityInfo.getCode());
        }
        city.setStatus(ObjectUtils.isEmpty(cityInfo.getStatus()) ? Status.ACTIVE : cityInfo.getStatus());
        city.setCreateDate(new Date(System.currentTimeMillis()));

        return cityDao.save(city);
    }

    @Override
    public City updateCity(CityInfo cityInfo) throws LogicException {
        if (ObjectUtils.isEmpty(cityInfo))
            throw new LogicException(ErrorCode.DATA_NULL);

        City city = findByCityId(cityInfo.getCityId());
        if (ObjectUtils.isEmpty(city))
            throw new LogicException(ErrorCode.NOT_FOUND_CITY);

        if (!ObjectUtils.isEmpty(cityInfo.getName())) {
            city.setName(cityInfo.getName());
        }
        if (!ObjectUtils.isEmpty(cityInfo.getDescription())) {
            city.setDescription(cityInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(cityInfo.getCode())) {
            city.setCode(city.getCode());
        }
        if (!ObjectUtils.isEmpty(cityInfo.getStatus())) {
            city.setStatus(cityInfo.getStatus());
        }
        city.setModifiedDate(new Date(System.currentTimeMillis()));

        return cityDao.save(city);
    }

    @Override
    public ErrorCode deleteCity(Long cityId) throws LogicException {
        City city = findByCityId(cityId);
        if (ObjectUtils.isEmpty(city))
            throw new LogicException(ErrorCode.NOT_FOUND_CITY);

        city.setStatus(Status.DELETED);
        city.setModifiedDate(new Date(System.currentTimeMillis()));
        cityDao.save(city);
        return ErrorCode.OK;
    }
}
