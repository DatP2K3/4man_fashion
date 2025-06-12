package com.evo.profile.presentation.rest;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evo.common.dto.response.ApiResponses;
import com.evo.profile.application.dto.response.CashbackTransactionDTO;
import com.evo.profile.application.service.CashbackQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CashbackTransactionController {
    private final CashbackQueryService cashbackQueryService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/cashback-transaction")
    public ApiResponses<List<CashbackTransactionDTO>> getUserCashbackHistory() {
        List<CashbackTransactionDTO> cashbackTransactions = cashbackQueryService.getUserCashbackHistory();
        return ApiResponses.<List<CashbackTransactionDTO>>builder()
                .data(cashbackTransactions)
                .success(true)
                .code(200)
                .message("Cashback transactions retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
