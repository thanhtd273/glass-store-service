package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.StoreDao;
import com.thanhtd.glassstore.dto.StoreInfo;
import com.thanhtd.glassstore.model.City;
import com.thanhtd.glassstore.model.District;
import com.thanhtd.glassstore.model.Store;
import com.thanhtd.glassstore.model.Ward;
import com.thanhtd.glassstore.service.CityService;
import com.thanhtd.glassstore.service.DistrictService;
import com.thanhtd.glassstore.service.StoreService;
import com.thanhtd.glassstore.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;

    private final CityService cityService;

    private final DistrictService districtService;

    private final WardService wardService;

    @Override
    public List<Store> findAllStores() {
        return storeDao.findAllStores();
    }

    @Override
    public Store findByStoreId(Long storeId) throws LogicException {
        if (ObjectUtils.isEmpty(storeId)) throw new LogicException(ErrorCode.ID_NULL);

        return storeDao.findByStoreId(storeId);
    }

    @Override
    public Store createStore(StoreInfo storeInfo) throws LogicException {
        if (ObjectUtils.isEmpty(storeInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (storeInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Store store = new Store();
        store.setName(storeInfo.getName());
        store.setPhoneNumber(storeInfo.getPhoneNumber());

        City city = cityService.findByCityId(store.getCityId());
        if (ObjectUtils.isEmpty(city)) throw new LogicException(ErrorCode.NOT_FOUND_CITY);
        store.setCityId(city.getCityId());

        District district = districtService.findByDistrictId(store.getDistrictId());
        if (ObjectUtils.isEmpty(district)) throw new LogicException(ErrorCode.NOT_FOUND_DISTRICT);
        store.setDistrictId(district.getDistrictId());

        Ward ward = wardService.findByWardId(store.getWardId());
        if (ObjectUtils.isEmpty(ward)) throw new LogicException(ErrorCode.NOT_FOUND_WARD);
        store.setWardId(ward.getWardId());

        if (!ObjectUtils.isEmpty(storeInfo.getAddress())) {
            store.setAddress(storeInfo.getAddress());
        }

        store.setStatus(ObjectUtils.isEmpty(storeInfo.getStatus()) ? Status.ACTIVE : storeInfo.getStatus());
        store.setCreateDate(new Date());

        store = storeDao.save(store);
        return store;
    }

    @Override
    public Store updateStore(Long storeId, StoreInfo storeInfo) throws LogicException {
        if (ObjectUtils.isEmpty(storeInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Store store = findByStoreId(storeId);
        if (!ObjectUtils.isEmpty(storeInfo.getName())) {
            store.setName(storeInfo.getName());
        }
        if (!ObjectUtils.isEmpty(storeInfo.getCityId())) {
            City city = cityService.findByCityId(storeInfo.getCityId());
            if (ObjectUtils.isEmpty(city)) throw new LogicException(ErrorCode.NOT_FOUND_CITY);
            store.setCityId(city.getCityId());
        }
        if (!ObjectUtils.isEmpty(storeInfo.getDistrictId())) {
            District district = districtService.findByDistrictId(storeInfo.getDistrictId());
            if (ObjectUtils.isEmpty(district)) throw new LogicException(ErrorCode.NOT_FOUND_DISTRICT);
            store.setDistrictId(district.getDistrictId());
        }
        if (!ObjectUtils.isEmpty(storeInfo.getWardId())) {
            Ward ward = wardService.findByWardId(storeInfo.getWardId());
            if (ObjectUtils.isEmpty(ward)) throw new LogicException(ErrorCode.NOT_FOUND_WARD);
            store.setWardId(ward.getWardId());
        }
        if (!ObjectUtils.isEmpty(storeInfo.getAddress())) {
            store.setAddress(storeInfo.getAddress());
        }
        if (!ObjectUtils.isEmpty(storeInfo.getOpenTime())) {
            store.setOpenTime(storeInfo.getOpenTime());
        }
        if (!ObjectUtils.isEmpty(storeInfo.getCloseTime())) {
            store.setCloseTime(storeInfo.getCloseTime());
        }

        if (!ObjectUtils.isEmpty(storeInfo.getStatus())) {
            store.setStatus(storeInfo.getStatus());
        }
        store.setModifiedDate(new Date());

        store = storeDao.save(store);
        return store;
    }

    @Override
    public ErrorCode deleteStore(Long storeId) throws LogicException {
        Store store = findByStoreId(storeId);
        if (ObjectUtils.isEmpty(store))
            return ErrorCode.NOT_FOUND_STORE;

        store.setStatus(Status.DELETED);
        store.setModifiedDate(new Date());

        storeDao.save(store);
        return ErrorCode.OK;
    }
}
