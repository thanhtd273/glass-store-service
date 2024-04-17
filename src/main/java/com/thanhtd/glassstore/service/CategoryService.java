package com.thanhtd.glassstore.service;

import com.thanhtd.glassstore.core.common.ErrorCode;
import com.thanhtd.glassstore.core.exception.LogicException;
import com.thanhtd.glassstore.dto.CategoryInfo;
import com.thanhtd.glassstore.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();

    Category findByCategoryId(Long categoryId) throws LogicException;

    Boolean isExistedCategory(Long categoryId) throws LogicException;

    Category createCategory(CategoryInfo categoryInfo) throws LogicException;

    Category updateCategory(Long categoryId, CategoryInfo categoryInfo) throws LogicException;

    ErrorCode deleteCategory(Long categoryId) throws LogicException;
}
