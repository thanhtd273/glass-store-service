package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.CityInfo;
import com.thanhtd.glassstore.model.City;

import java.util.List;

public interface CityService {
    List<City> findAllCities();

    City findByCityId(Long cityId) throws LogicException;

    City findByName(String name) throws LogicException;

    List<City> searchByName(String name);

    City createCity(CityInfo cityInfo) throws LogicException;

    City updateCity(CityInfo cityInfo) throws LogicException;

    ErrorCode deleteCity(Long cityId) throws LogicException;
}
