package com.evo.product.application.service;

import java.util.List;
import java.util.UUID;

import com.evo.common.dto.response.ProductDTO;

public interface ProductQueryService {
    ProductDTO getById(UUID id);

    List<ProductDTO> getAllProductsWithNoDiscount();
}
