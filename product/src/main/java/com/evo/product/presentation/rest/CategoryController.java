package com.evo.product.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.service.impl.CategoryCommandService;
import com.evo.product.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCommandService categoryCommandService;

    @PreAuthorize("hasPermission(null, 'category.create')")
     @PostMapping("/category")
    ApiResponses<Category> createCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
         Category category = categoryCommandService.createCategory(createOrUpdateCategoryRequest);
        return ApiResponses.<Category>builder()
                .data(category)
                .success(true)
                .code(201)
                .message("Category created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasPermission(null, 'category.update')")
    @PutMapping("/category")
    ApiResponses<Category> updateCategory(@RequestBody CreateOrUpdateCategoryRequest createOrUpdateCategoryRequest) {
        Category category = categoryCommandService.updateCategory(createOrUpdateCategoryRequest);
        return ApiResponses.<Category>builder()
                .data(category)
                .success(true)
                .code(200)
                .message("Category updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
