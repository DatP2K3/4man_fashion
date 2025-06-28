package com.evo.product.presentation.rest.impl;

import java.util.List;
import java.util.UUID;

import com.evo.product.presentation.rest.DiscountController;
import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.Response;
import com.evo.product.application.dto.response.DiscountDTO;
import com.evo.product.application.service.DiscountQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscountControllerImpl implements DiscountController {
    private final DiscountQueryService discountQueryService;

    @Override
    public Response<List<DiscountDTO>> getAll() {
        return Response.of(this.discountQueryService.getAll());
    }

    @Override
    public Response<DiscountDTO> getById(@PathVariable UUID id) {
        return Response.of(this.discountQueryService.getById(id));
    }
}
