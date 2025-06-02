package com.evo.product.application.service;

import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.response.CategoryDTO;

import java.util.UUID;

public interface CategoryCommandService {
    CategoryDTO createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    CategoryDTO updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    void visibilityCategory(UUID id);
}
