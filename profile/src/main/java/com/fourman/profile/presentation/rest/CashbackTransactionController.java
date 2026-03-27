package com.fourman.profile.presentation.rest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.common.dto.response.Response;
import com.fourman.profile.application.dto.response.CashbackTransactionDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cashback Transaction API")
@RequestMapping("/api")
@Validated
public interface CashbackTransactionController {

    @Operation(summary = "Get user cashback history")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cashback-transaction")
    Response<List<CashbackTransactionDTO>> getUserCashbackHistory();
}
