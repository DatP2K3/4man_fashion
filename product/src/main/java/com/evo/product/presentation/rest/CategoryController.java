package com.evo.product.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.response.CategoryDTO;
import com.evo.product.application.service.impl.CategoryCommandService;
import com.evo.product.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCommandService categoryCommandService;

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
        List<CategoryDTO> categories = categoryCommandService.getCategories();
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
