package com.fourman.profile.application.service;

import java.util.List;

import com.fourman.profile.application.dto.response.CashbackTransactionDTO;

public interface CashbackQueryService {
    List<CashbackTransactionDTO> getUserCashbackHistory();
}
