package com.evo.product.application.service;

import java.util.List;
import java.util.UUID;

import com.evo.product.application.dto.response.CategoryDTO;

public interface CategoryQueryService {
    List<CategoryDTO> getCategories();

    List<CategoryDTO> getCategoriesByProductType(String productType);

    CategoryDTO getCategoryById(UUID id);
}
