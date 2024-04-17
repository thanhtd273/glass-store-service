package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.ColorInfo;
import com.thanhtd.glassstore.model.Color;

import java.util.List;

public interface ColorService {
    List<Color> findAllColors();

    Color findByColorId(Long colorId) throws LogicException;

    Color findByHexCode(String hexCode);

    Color createColor(ColorInfo colorInfo) throws LogicException;

    Color updateColor(Long colorId, ColorInfo colorInfo) throws LogicException;

    ErrorCode deleteColor(Long colorId) throws LogicException;
}
