package com.evo.product.presentation.rest.impl;

import java.util.List;
import java.util.UUID;

import com.evo.product.presentation.rest.ProductController;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.Response;
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
    public Response<ProductDTO> createProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        return Response.of(this.productCommandService.createProduct(createOrUpdateProductRequest));
    }

    @Override
    public Response<ProductDTO> updateProduct(@RequestBody CreateOrUpdateProductRequest createOrUpdateProductRequest) {
        return Response.of(this.productCommandService.updateProduct(createOrUpdateProductRequest));
    }

    @Override
    public Response<ProductDTO> getProduct(@PathVariable UUID id) {
        return Response.of(this.productQueryService.getById(id));
    }

    @Override
    public Response<ProductDTO> createDiscount(@RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        return Response.of(this.productCommandService.createDiscount(createOrUpdateDiscountRequest));
    }

    @Override
    public Response<ProductDTO> updateDiscount(@RequestBody CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest) {
        return Response.of(this.productCommandService.updateDiscount(createOrUpdateDiscountRequest));
    }

    @Override
    public Response<List<ProductDTO>> getAllProductsWithNoDiscount() {
        return Response.of(this.productQueryService.getAllProductsWithNoDiscount());
    }

    @Override
    public Response<ProductDTO> toggleProductVisibility(@PathVariable UUID id) {
        return Response.of(this.productCommandService.toggleProductVisibility(id));
    }
}
