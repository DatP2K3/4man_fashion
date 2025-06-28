package com.evo.product.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evo.product.application.dto.response.DiscountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Discount API")
@RequestMapping("/api")
@Validated
public interface DiscountController {

    @Operation(summary = "Get all discounts")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/discounts")
    Response<List<DiscountDTO>> getAll();

    @Operation(summary = "Get discount by ID")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/discounts/{id}")
    Response<DiscountDTO> getById(@PathVariable UUID id);
}
