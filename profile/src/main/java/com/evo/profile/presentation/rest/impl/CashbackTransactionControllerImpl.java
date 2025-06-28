package com.evo.profile.presentation.rest;

import java.util.List;

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
public class CashbackTransactionControllerImpl implements CashbackTransactionController {
    private final CashbackQueryService cashbackQueryService;

    @Override
    public ApiResponses<List<CashbackTransactionDTO>> getUserCashbackHistory() {
        return ApiResponses.of(this.cashbackQueryService.getUserCashbackHistory());
    }
}
