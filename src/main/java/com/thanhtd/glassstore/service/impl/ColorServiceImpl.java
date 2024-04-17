package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.ColorDao;
import com.thanhtd.glassstore.dto.ColorInfo;
import com.thanhtd.glassstore.model.Color;
import com.thanhtd.glassstore.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorDao colorDao;

    @Override
    public List<Color> findAllColors() {
        return colorDao.findAllColors();
    }

    @Override
    public Color findByColorId(Long colorId) throws LogicException {
        if (ObjectUtils.isEmpty(colorId))
            throw new LogicException(ErrorCode.ID_NULL);
        return colorDao.findByColorId(colorId);
    }

    @Override
    public Color findByHexCode(String hexCode) {
        return colorDao.findByHexCode(hexCode);
    }

    @Override
    public Color createColor(ColorInfo colorInfo) throws LogicException {
        if (ObjectUtils.isEmpty(colorInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (colorInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Color color = new Color();
        color.setName(color.getName());
        color.setHexCode(color.getHexCode());
        color.setStatus(ObjectUtils.isEmpty(colorInfo.getStatus()) ? Status.ACTIVE : colorInfo.getStatus());
        color.setCreateDate(new Date(System.currentTimeMillis()));
        return colorDao.save(color);
    }

    @Override
    public Color updateColor(Long colorId, ColorInfo colorInfo) throws LogicException {
        if (ObjectUtils.isEmpty(colorInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Color color = findByColorId(colorId);
        if (ObjectUtils.isEmpty(color))
            throw new LogicException(ErrorCode.NOT_FOUND_COLOR);

        if (!ObjectUtils.isEmpty(colorInfo.getName())) {
            color.setName(colorInfo.getName());
        }
        if (!ObjectUtils.isEmpty(colorInfo.getHexCode())) {
            color.setName(colorInfo.getHexCode());
        }
        if (!ObjectUtils.isEmpty(colorInfo.getStatus())) {
            color.setStatus(color.getStatus());
        }
        color.setModifiedDate(new Date(System.currentTimeMillis()));

        return colorDao.save(color);
    }

    @Override
    public ErrorCode deleteColor(Long colorId) throws LogicException {
        Color color = findByColorId(colorId);
        if (ObjectUtils.isEmpty(color))
            return ErrorCode.NOT_FOUND_COLOR;

        color.setStatus(Status.DELETED);
        color.setModifiedDate(new Date(System.currentTimeMillis()));
        colorDao.save(color);
        return ErrorCode.OK;
    }
}
