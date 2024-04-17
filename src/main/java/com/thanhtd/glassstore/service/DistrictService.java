package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.DistrictInfo;
import com.thanhtd.glassstore.model.District;
import lombok.extern.java.Log;

import java.util.List;

public interface DistrictService {
    List<District> findAllDistricts();

    District findByDistrictId(Long districtId) throws LogicException;

    District findByName(String name) throws LogicException;

    List<District> findByCityId(Long cityId) throws LogicException;

    List<District> searchByName(String name) throws LogicException;

    District createDistrict(DistrictInfo districtInfo) throws LogicException;

    District updateDistrict(DistrictInfo districtInfo) throws LogicException;

    ErrorCode deleteDistrict(Long districtId) throws LogicException;
}
