package com.evo.product.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.dto.response.ProductDTO;
import com.evo.product.application.service.ProductCommandService;
import com.evo.product.application.service.ProductQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @PostMapping("/products")
    public ApiResponses<ProductDTO> createProduct(
            @RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        ProductDTO productDTO = productCommandService.createProduct(createOrUpdateProductRequest);
        return ApiResponses.<ProductDTO>builder()
                .data(productDTO)
                .success(true)
                .code(201)
                .message("Product created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/products")
    public ApiResponses<ProductDTO> updateProduct(
            @RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        ProductDTO productDTO = productCommandService.updateProduct(createOrUpdateProductRequest);
        return ApiResponses.<ProductDTO>builder()
                .data(productDTO)
                .success(true)
                .code(200)
                .message("Product updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/products/{id}")
    public ApiResponses<ProductDTO> getProduct(@PathVariable UUID id) {
        ProductDTO productDTO = productQueryService.getById(id);
        return ApiResponses.<ProductDTO>builder()
                .data(productDTO)
                .success(true)
                .code(200)
                .message("Product retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/products/discounts")
    public ApiResponses<ProductDTO> createDiscount(
            @RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        ProductDTO productDTO = productCommandService.createDiscount(createOrUpdateDiscountRequest);
        return ApiResponses.<ProductDTO>builder()
                .data(productDTO)
                .success(true)
                .code(201)
                .message("Discount created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/products/discounts")
    public ApiResponses<ProductDTO> updateDiscount(
            @RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        ProductDTO productDTO = productCommandService.updateDiscount(createOrUpdateDiscountRequest);
        return ApiResponses.<ProductDTO>builder()
                .data(productDTO)
                .success(true)
                .code(200)
                .message("Discount updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/products-with-no-discount")
    public ApiResponses<List<ProductDTO>> getAllProductsWithNoDiscount() {
        List<ProductDTO> productDTOs = productQueryService.getAllProductsWithNoDiscount();
        return ApiResponses.<List<ProductDTO>>builder()
                .data(productDTOs)
                .success(true)
                .code(200)
                .message("Products retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
