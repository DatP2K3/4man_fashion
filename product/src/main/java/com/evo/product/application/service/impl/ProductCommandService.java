package com.evo.product.application.service.impl;

import com.evo.product.application.dto.request.CreateOrUpdateProductRequest;
import com.evo.product.application.dto.response.ProductDTO;

public interface ProductCommandService {
    ProductDTO createProduct(CreateOrUpdateProductRequest createOrUpdateProductRequest);
}
