package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.ImageInfo;
import com.thanhtd.glassstore.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAllImages();

    Image findByImageId(Long imageId) throws LogicException;

    Image createImage(ImageInfo imageInfo) throws LogicException;

    Image updateImage(Long imageId, ImageInfo imageInfo) throws LogicException;

    ErrorCode deleteImage(Long imageId) throws LogicException;
}
