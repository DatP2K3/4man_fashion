package com.evo.product.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.response.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Category API")
@RequestMapping("/api")
@Validated
public interface CategoryController {

    @Operation(summary = "Create category")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/category")
    Response<CategoryDTO> createCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    @Operation(summary = "Update category")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/category")
    Response<CategoryDTO> updateCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest);

    @Operation(summary = "Get all categories")
    @GetMapping("/category")
    Response<List<CategoryDTO>> getCategory();

    @Operation(summary = "Get category by ID")
    @GetMapping("/category/{id}")
    Response<CategoryDTO> getCategoryById(@PathVariable UUID id);

    @Operation(summary = "Get categories by product type")
    @GetMapping("/category/category-by-productType")
    Response<List<CategoryDTO>> getCategoryByProductType(@RequestParam String productType);

    @Operation(summary = "Toggle category visibility")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/category/{id}/visibility")
    Response<Void> visibilityCategory(@PathVariable UUID id);
}
