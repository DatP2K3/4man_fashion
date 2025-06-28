package com.evo.product.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.application.service.CategoryCommandService;
import com.evo.product.application.service.CategoryQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    @Override
    public ApiResponses<CategoryDTO> createCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        return ApiResponses.of(this.categoryCommandService.createCategory(createOrUpdateCategoryRequest));
    }

    @Override
    public ApiResponses<CategoryDTO> updateCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        return ApiResponses.of(this.categoryCommandService.updateCategory(createOrUpdateCategoryRequest));
    }

    @Override
    public ApiResponses<List<CategoryDTO>> getCategory() {
        return ApiResponses.of(this.categoryQueryService.getCategories());
    }

    @Override
    public ApiResponses<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        return ApiResponses.of(this.categoryQueryService.getCategoryById(id));
    }

    @Override
    public ApiResponses<List<CategoryDTO>> getCategoryByProductType(@RequestParam String productType) {
        return ApiResponses.of(this.categoryQueryService.getCategoriesByProductType(productType));
    }

    @Override
    public ApiResponses<Void> visibilityCategory(@PathVariable UUID id) {
        this.categoryCommandService.visibilityCategory(id);
        return ApiResponses.ok();
    }
}
