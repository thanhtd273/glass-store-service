package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.FeatureInfo;
import com.thanhtd.glassstore.model.Feature;

import java.util.List;

public interface FeatureService {
    Feature createFeature(FeatureInfo featureInfo) throws LogicException;
    Feature updateFeature(Long featureId, FeatureInfo featureInfo) throws LogicException;
    ErrorCode deleteFeature(Long featureId) throws LogicException;
    List<Feature> findAllFeatures();
    Feature findByFeatureId(Long featureId) throws LogicException;

}
