package com.evo.product.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evo.product.application.dto.response.DiscountDTO;
import com.evo.product.application.service.DiscountQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscountControllerImpl implements DiscountController {
    private final DiscountQueryService discountQueryService;

    @Override
    public ApiResponses<List<DiscountDTO>> getAll() {
        return ApiResponses.of(this.discountQueryService.getAll());
    }

    @Override
    public ApiResponses<DiscountDTO> getById(@PathVariable UUID id) {
        return ApiResponses.of(this.discountQueryService.getById(id));
    }
}
