package com.evo.product.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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
public class CategoryController {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/category")
    ApiResponses<CategoryDTO> createCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CategoryDTO categoryDTO = categoryCommandService.createCategory(createOrUpdateCategoryRequest);
        return ApiResponses.<CategoryDTO>builder()
                .data(categoryDTO)
                .success(true)
                .code(201)
                .message("Category created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/category")
    ApiResponses<CategoryDTO> updateCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        CategoryDTO categoryDTO = categoryCommandService.updateCategory(createOrUpdateCategoryRequest);
        return ApiResponses.<CategoryDTO>builder()
                .data(categoryDTO)
                .success(true)
                .code(200)
                .message("Category updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/category")
    ApiResponses<List<CategoryDTO>> getCategory() {
        List<CategoryDTO> categories = categoryQueryService.getCategories();
        return ApiResponses.<List<CategoryDTO>>builder()
                .data(categories)
                .success(true)
                .code(200)
                .message("Category retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/category/{id}")
    ApiResponses<CategoryDTO> getCategoryById(@PathVariable UUID id) {
        CategoryDTO categoryDTO = categoryQueryService.getCategoryById(id);
        return ApiResponses.<CategoryDTO>builder()
                .data(categoryDTO)
                .success(true)
                .code(200)
                .message("Category retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/category/category-by-productType")
    ApiResponses<List<CategoryDTO>> getCategoryByProductType(@RequestParam String productType) {
        List<CategoryDTO> categories = categoryQueryService.getCategoriesByProductType(productType);
        return ApiResponses.<List<CategoryDTO>>builder()
                .data(categories)
                .success(true)
                .code(200)
                .message("Category retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
