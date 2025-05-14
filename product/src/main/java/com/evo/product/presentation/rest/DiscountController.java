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
public class DiscountController {
    private final DiscountQueryService discountQueryService;

    @GetMapping("/discounts")
    public ApiResponses<List<DiscountDTO>> getAll() {
        List<DiscountDTO> discountDTOs = discountQueryService.getAll();
        return ApiResponses.<List<DiscountDTO>>builder()
                .data(discountDTOs)
                .success(true)
                .code(200)
                .message("Discounts retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/discounts/{id}")
    public ApiResponses<DiscountDTO> getById(@PathVariable UUID id) {
        DiscountDTO discountDTO = discountQueryService.getById(id);
        return ApiResponses.<DiscountDTO>builder()
                .data(discountDTO)
                .success(true)
                .code(200)
                .message("Discount retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
