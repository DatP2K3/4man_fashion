package com.evo.product.application.service;

import java.util.List;

import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.response.CategoryDTO;

public interface CategoryCommandService {
    CategoryDTO createCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    CategoryDTO updateCategory(CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    List<CategoryDTO> getCategories();
}
