package com.evo.product.application.service.impl;

import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.request.CreateTagDescriptionRequest;
import com.evo.product.domain.Category;

import java.util.UUID;

public interface CategoryCommandService {
    Category createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);
    Category updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);
}
