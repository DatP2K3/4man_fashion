package com.evo.product.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.ProductDTO;
import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.service.ProductCommandService;
import com.evo.product.application.service.ProductQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @Override
    public ApiResponses<ProductDTO> createProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        return ApiResponses.of(this.productCommandService.createProduct(createOrUpdateProductRequest));
    }

    @Override
    public ApiResponses<ProductDTO> updateProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        return ApiResponses.of(this.productCommandService.updateProduct(createOrUpdateProductRequest));
    }

    @Override
    public ApiResponses<ProductDTO> getProduct(@PathVariable UUID id) {
        return ApiResponses.of(this.productQueryService.getById(id));
    }

    @Override
    public ApiResponses<ProductDTO> createDiscount(@RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        return ApiResponses.of(this.productCommandService.createDiscount(createOrUpdateDiscountRequest));
    }

    @Override
    public ApiResponses<ProductDTO> updateDiscount(@RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        return ApiResponses.of(this.productCommandService.updateDiscount(createOrUpdateDiscountRequest));
    }

    @Override
    public ApiResponses<List<ProductDTO>> getAllProductsWithNoDiscount() {
        return ApiResponses.of(this.productQueryService.getAllProductsWithNoDiscount());
    }

    @Override
    public ApiResponses<ProductDTO> toggleProductVisibility(@PathVariable UUID id) {
        return ApiResponses.of(this.productCommandService.toggleProductVisibility(id));
    }
}
