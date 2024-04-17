package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.StoreInfo;
import com.thanhtd.glassstore.model.Store;

import java.util.List;

public interface StoreService {
    List<Store> findAllStores();

    Store findByStoreId(Long storeId) throws LogicException;

    Store createStore(StoreInfo storeInfo) throws LogicException;

    Store updateStore(Long storeId, StoreInfo storeInfo) throws LogicException;

    ErrorCode deleteStore(Long storeId) throws LogicException;
}
