package com.evo.product.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evo.common.dto.response.ProductDTO;
import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Product API")
@RequestMapping("/api")
@Validated
public interface ProductController {

    @Operation(summary = "Create product")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products")
    Response<ProductDTO> createProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest);

    @Operation(summary = "Update product")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products")
    Response<ProductDTO> updateProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest);

    @Operation(summary = "Get product by ID")
    @GetMapping("/products/{id}")
    Response<ProductDTO> getProduct(@PathVariable UUID id);

    @Operation(summary = "Create discount")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products/discounts")
    Response<ProductDTO> createDiscount(@RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    @Operation(summary = "Update discount")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products/discounts")
    Response<ProductDTO> updateDiscount(@RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    @Operation(summary = "Get products with no discount")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/products-with-no-discount")
    Response<List<ProductDTO>> getAllProductsWithNoDiscount();

    @Operation(summary = "Toggle product visibility")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products/{id}/visibility")
    Response<ProductDTO> toggleProductVisibility(@PathVariable UUID id);
}
