package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.ShapeDao;
import com.thanhtd.glassstore.dto.ShapeInfo;
import com.thanhtd.glassstore.model.Shape;
import com.thanhtd.glassstore.service.ShapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShapeServiceImpl implements ShapeService {

    private final ShapeDao shapeDao;

    @Override
    public List<Shape> findAllShapes() {
        return shapeDao.findAllShapes();
    }

    @Override
    public Shape findByShapeId(Long shapeId) throws LogicException {
        if (ObjectUtils.isEmpty(shapeId))
            throw new LogicException(ErrorCode.ID_NULL);
        return shapeDao.findByShapeId(shapeId);
    }

    @Override
    public Shape createShape(ShapeInfo shapeInfo) throws LogicException {
        if (ObjectUtils.isEmpty(shapeInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (shapeInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Shape shape = new Shape();
        shape.setName(shape.getName());
        shape.setStatus(ObjectUtils.isEmpty(shapeInfo.getStatus()) ? Status.ACTIVE : shapeInfo.getStatus());
        shape.setCreateDate(new Date(System.currentTimeMillis()));
        return shapeDao.save(shape);
    }

    @Override
    public Shape updateShape(Long shapeId, ShapeInfo shapeInfo) throws LogicException {
        if (ObjectUtils.isEmpty(shapeInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Shape shape = findByShapeId(shapeId);
        if (ObjectUtils.isEmpty(shape))
            throw new LogicException(ErrorCode.NOT_FOUND_SHAPE);

        if (!ObjectUtils.isEmpty(shapeInfo.getName())) {
            shape.setName(shapeInfo.getName());
        }
        if (!ObjectUtils.isEmpty(shapeInfo.getStatus())) {
            shape.setStatus(shape.getStatus());
        }
        shape.setModifiedDate(new Date(System.currentTimeMillis()));

        return shapeDao.save(shape);
    }

    @Override
    public ErrorCode deleteShape(Long shapeId) throws LogicException {
        Shape shape = findByShapeId(shapeId);
        if (ObjectUtils.isEmpty(shape))
            return ErrorCode.NOT_FOUND_SHAPE;

        shape.setStatus(Status.DELETED);
        shape.setModifiedDate(new Date(System.currentTimeMillis()));
        shapeDao.save(shape);
        return ErrorCode.OK;
    }
}
