package com.fourman.product.application.service;

import java.util.UUID;

import com.fourman.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.fourman.product.application.dto.response.CategoryDTO;

public interface CategoryCommandService {
    CategoryDTO createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    CategoryDTO updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    void visibilityCategory(UUID id);
}
