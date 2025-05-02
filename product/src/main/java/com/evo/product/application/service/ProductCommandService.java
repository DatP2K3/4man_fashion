package com.evo.product.application.service;

import com.evo.product.application.dto.request.CreateOrUpdateDiscountRequest;
import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.common.dto.response.ProductDTO;

public interface ProductCommandService {
    ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);

    ProductDTO updateProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);

    ProductDTO createDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);

    ProductDTO updateDiscount(CreateOrUpdateDiscountRequest createOrUpdateDiscountRequest);
}
