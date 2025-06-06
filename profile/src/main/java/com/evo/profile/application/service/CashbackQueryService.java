package com.evo.profile.application.service;

import java.util.List;
import java.util.UUID;

import com.evo.profile.application.dto.response.CashbackTransactionDTO;
import com.evo.profile.domain.CashbackTransaction;

public interface CashbackQueryService {
    List<CashbackTransactionDTO> getUserCashbackHistory();
}
