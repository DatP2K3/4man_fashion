package com.evo.profile.application.service;

import java.util.List;

import com.evo.profile.application.dto.response.CashbackTransactionDTO;

public interface CashbackQueryService {
    List<CashbackTransactionDTO> getUserCashbackHistory();
}
