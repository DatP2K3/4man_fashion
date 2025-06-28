package com.evo.profile.presentation.rest.impl;

import java.util.List;

import com.evo.profile.presentation.rest.CashbackTransactionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evo.common.dto.response.Response;
import com.evo.profile.application.dto.response.CashbackTransactionDTO;
import com.evo.profile.application.service.CashbackQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CashbackTransactionControllerImpl implements CashbackTransactionController {
    private final CashbackQueryService cashbackQueryService;

    @Override
    public Response<List<CashbackTransactionDTO>> getUserCashbackHistory() {
        return Response.of(this.cashbackQueryService.getUserCashbackHistory());
    }
}
