package com.fourman.profile.presentation.rest.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourman.common.dto.response.Response;
import com.fourman.common.webapp.config.inbound.InboundRequest;
import com.fourman.profile.application.dto.response.CashbackTransactionDTO;
import com.fourman.profile.application.service.CashbackQueryService;
import com.fourman.profile.presentation.rest.CashbackTransactionController;

import lombok.RequiredArgsConstructor;

@InboundRequest
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
