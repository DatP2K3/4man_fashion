package com.fourman.product.presentation.rest.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.response.Response;
import com.fourman.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.fourman.product.application.dto.response.CategoryDTO;
import com.fourman.product.application.service.CategoryCommandService;
import com.fourman.product.application.service.CategoryQueryService;
import com.fourman.product.presentation.rest.CategoryController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    @Override
    public Response<CategoryDTO> createCategory(
            @RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        return Response.of(this.categoryCommandService.createCategory(createOrUpdateCategoryRequest));
    }

    @Override
    public Response<CategoryDTO> updateCategory(
            @RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        return Response.of(this.categoryCommandService.updateCategory(createOrUpdateCategoryRequest));
    }

    @Override
    public Response<List<CategoryDTO>> getCategory() {
        return Response.of(this.categoryQueryService.getCategories());
    }

    @Override
    public Response<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        return Response.of(this.categoryQueryService.getCategoryById(id));
    }

    @Override
    public Response<List<CategoryDTO>> getCategoryByProductType(@RequestParam String productType) {
        return Response.of(this.categoryQueryService.getCategoriesByProductType(productType));
    }

    @Override
    public Response<Void> visibilityCategory(@PathVariable UUID id) {
        this.categoryCommandService.visibilityCategory(id);
        return Response.ok();
    }
}
