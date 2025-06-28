package com.evo.product.application.service;

import java.util.UUID;

import com.evo.common.dto.response.ProductDTO;
import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;

public interface ProductCommandService {
    ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);

    ProductDTO updateProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);

    ProductDTO createDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    ProductDTO updateDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    ProductDTO toggleProductVisibility(UUID id);
}
