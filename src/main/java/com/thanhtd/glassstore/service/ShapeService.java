package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.ShapeInfo;
import com.thanhtd.glassstore.model.Shape;

import java.util.List;

public interface ShapeService {
    List<Shape> findAllShapes();

    Shape findByShapeId(Long shapeId) throws LogicException;

    Shape createShape(ShapeInfo shapeInfo) throws LogicException;

    Shape updateShape(Long shapeId, ShapeInfo shapeInfo) throws LogicException;

    ErrorCode deleteShape(Long shapeId) throws LogicException;
}
