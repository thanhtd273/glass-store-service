package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.ImageDao;
import com.thanhtd.glassstore.dto.ImageInfo;
import com.thanhtd.glassstore.model.Image;
import com.thanhtd.glassstore.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    @Override
    public List<Image> findAllImages() {
        return imageDao.findAllImages();
    }

    @Override
    public Image findByImageId(Long imageId) throws LogicException {
        if (ObjectUtils.isEmpty(imageId))
            throw new LogicException(ErrorCode.ID_NULL);
        return imageDao.findByImageId(imageId);
    }

    @Override
    public Image createImage(ImageInfo imageInfo) throws LogicException {
        if (ObjectUtils.isEmpty(imageInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (imageInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Image image = new Image();
        image.setUrl(image.getUrl());
        image.setStatus(ObjectUtils.isEmpty(imageInfo.getStatus()) ? Status.ACTIVE : imageInfo.getStatus());
        image.setCreateDate(new Date(System.currentTimeMillis()));
        return imageDao.save(image);
    }

    @Override
    public Image updateImage(Long imageId, ImageInfo imageInfo) throws LogicException {
        if (ObjectUtils.isEmpty(imageInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Image image = findByImageId(imageId);
        if (ObjectUtils.isEmpty(image))
            throw new LogicException(ErrorCode.NOT_FOUND_IMAGE);

        if (!ObjectUtils.isEmpty(imageInfo.getUrl())) {
            image.setUrl(imageInfo.getUrl());
        }
        if (!ObjectUtils.isEmpty(imageInfo.getStatus())) {
            image.setStatus(image.getStatus());
        }
        image.setModifiedDate(new Date(System.currentTimeMillis()));

        return imageDao.save(image);
    }

    @Override
    public ErrorCode deleteImage(Long imageId) throws LogicException {
        Image image = findByImageId(imageId);
        if (ObjectUtils.isEmpty(image))
            return ErrorCode.NOT_FOUND_IMAGE;

        image.setStatus(Status.DELETED);
        image.setModifiedDate(new Date(System.currentTimeMillis()));
        imageDao.save(image);
        return ErrorCode.OK;
    }
}
