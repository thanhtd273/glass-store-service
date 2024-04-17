package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.FeatureDao;
import com.thanhtd.glassstore.dto.FeatureInfo;
import com.thanhtd.glassstore.model.Feature;
import com.thanhtd.glassstore.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureDao featureDao;

    @Autowired
    public FeatureServiceImpl(FeatureDao featureDao) {
        this.featureDao = featureDao;
    }
    @Override
    public List<Feature> findAllFeatures() {
        return featureDao.findAllFeatures();
    }

    @Override
    public Feature findByFeatureId(Long featureId) throws LogicException {
        if (ObjectUtils.isEmpty(featureId)) throw new LogicException(ErrorCode.ID_NULL);

        return featureDao.findByFeatureId(featureId);
    }

    @Override
    public Feature createFeature(FeatureInfo featureInfo) throws LogicException {
        if (ObjectUtils.isEmpty(featureInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (featureInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Feature feature = new Feature();
        feature.setName(featureInfo.getName());
        if (!ObjectUtils.isEmpty(featureInfo.getDescription())) {
            feature.setDescription(feature.getDescription());
        }
        feature.setStatus(ObjectUtils.isEmpty(featureInfo.getStatus()) ? Status.ACTIVE : featureInfo.getStatus());
        feature.setCreateDate(new Date());

        feature = featureDao.save(feature);
        return feature;
    }

    @Override
    public Feature updateFeature(Long featureId, FeatureInfo featureInfo) throws LogicException {
        if (ObjectUtils.isEmpty(featureInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Feature feature = findByFeatureId(featureId);
        if (!ObjectUtils.isEmpty(featureInfo.getName())) {
            feature.setName(featureInfo.getName());
        }
        if (!ObjectUtils.isEmpty(featureInfo.getDescription())) {
            feature.setDescription(featureInfo.getDescription());
        }
        if (!ObjectUtils.isEmpty(featureInfo.getStatus())) {
            feature.setStatus(featureInfo.getStatus());
        }
        feature.setModifiedDate(new Date());

        feature = featureDao.save(feature);
        return feature;
    }

    @Override
    public ErrorCode deleteFeature(Long featureId) throws LogicException {
        Feature feature = findByFeatureId(featureId);
        if (ObjectUtils.isEmpty(feature))
            return ErrorCode.NOT_FOUND_DATA;

        feature.setStatus(Status.DELETED);
        feature.setModifiedDate(new Date());

        featureDao.save(feature);
        return ErrorCode.OK;
    }
}
