package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.WardInfo;
import com.thanhtd.glassstore.model.Ward;

import java.util.List;

public interface WardService {

    List<Ward> findAllWards();

    Ward findByWardId(Long wardId) throws LogicException;

    Ward findByName(String name) throws LogicException;

    List<Ward> findByCityId(Long cityId) throws LogicException;

    List<Ward> findByDistrictId(Long districtId) throws LogicException;

    List<Ward> searchByName(String name);

    Ward createWard(WardInfo wardInfo) throws LogicException;

    Ward updateWard(WardInfo wardInfo) throws LogicException;

    ErrorCode deleteWard(Long wardId) throws LogicException;
}
