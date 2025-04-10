package com.evo.product.application.service.impl;

import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.request.CreateTagDescriptionRequest;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.domain.Category;

import java.util.List;
import java.util.UUID;

public interface  CategoryCommandService {
    CategoryDTO createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);
    CategoryDTO updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    List<CategoryDTO> getCategories();
}
