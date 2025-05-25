package com.evo.profile.application.service;

import com.evo.profile.domain.command.ProcessCashbackCmd;
import com.evo.profile.domain.command.UseCashbackCmd;

public interface CashbackCommandService {
    void processCashback(ProcessCashbackCmd cmd);

    void useCashback(UseCashbackCmd cmd);
}
