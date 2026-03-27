package com.fourman.product.application.service;

import java.util.UUID;

import com.fourman.common.dto.response.ProductDTO;
import com.fourman.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.fourman.product.application.dto.request.CreateOrUpdateProductRequest;

public interface ProductCommandService {
    ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);

    ProductDTO updateProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);

    ProductDTO createDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    ProductDTO updateDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    ProductDTO toggleProductVisibility(UUID id);
}
