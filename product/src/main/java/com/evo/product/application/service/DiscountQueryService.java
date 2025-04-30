package com.evo.product.application.service;

import java.util.List;
import java.util.UUID;

import com.evo.product.application.dto.response.DiscountDTO;

public interface DiscountQueryService {
    List<DiscountDTO> getAll();

    DiscountDTO getById(UUID id);
}
