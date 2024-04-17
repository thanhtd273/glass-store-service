package com.thanhtd.glassstore.service.impl;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.constant.Status;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dao.CategoryDao;
import com.thanhtd.glassstore.dto.CategoryInfo;
import com.thanhtd.glassstore.model.Category;
import com.thanhtd.glassstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public List<Category> findAllCategories() {
        return categoryDao.findAllCategories();
    }

    @Override
    public Category findByCategoryId(Long categoryId) throws LogicException {
        if (ObjectUtils.isEmpty(categoryId))
            throw new LogicException(ErrorCode.ID_NULL);
        return categoryDao.findByCategoryId(categoryId);
    }

    @Override
    public Boolean isExistedCategory(Long categoryId) throws LogicException {
        Category category = findByCategoryId(categoryId);
        return !ObjectUtils.isEmpty(category);
    }

    @Override
    public Category createCategory(CategoryInfo categoryInfo) throws LogicException {
        if (ObjectUtils.isEmpty(categoryInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        if (categoryInfo.isMissingInfo())
            throw new LogicException(ErrorCode.MISSING_DATA);

        Category category = new Category();
        category.setName(category.getName());
        category.setStatus(ObjectUtils.isEmpty(categoryInfo.getStatus()) ? Status.ACTIVE : categoryInfo.getStatus());
        category.setCreateDate(new Date(System.currentTimeMillis()));
        return categoryDao.save(category);
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryInfo categoryInfo) throws LogicException {
        if (ObjectUtils.isEmpty(categoryInfo))
            throw new LogicException(ErrorCode.DATA_NULL);
        Category category = findByCategoryId(categoryId);
        if (ObjectUtils.isEmpty(category))
            throw new LogicException(ErrorCode.NOT_FOUND_CATEGORY);

        if (!ObjectUtils.isEmpty(categoryInfo.getName())) {
            category.setName(categoryInfo.getName());
        }
        if (!ObjectUtils.isEmpty(categoryInfo.getStatus())) {
            category.setStatus(category.getStatus());
        }
        category.setModifiedDate(new Date(System.currentTimeMillis()));

        return categoryDao.save(category);
    }

    @Override
    public ErrorCode deleteCategory(Long categoryId) throws LogicException {
        Category category = findByCategoryId(categoryId);
        if (ObjectUtils.isEmpty(category))
            return ErrorCode.NOT_FOUND_CATEGORY;

        category.setStatus(Status.DELETED);
        category.setModifiedDate(new Date(System.currentTimeMillis()));
        categoryDao.save(category);
        return ErrorCode.OK;
    }
}
