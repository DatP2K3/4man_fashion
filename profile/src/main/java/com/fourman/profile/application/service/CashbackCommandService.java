package com.fourman.profile.application.service;

import com.fourman.profile.domain.command.ProcessCashbackCmd;
import com.fourman.profile.domain.command.UseCashbackCmd;

public interface CashbackCommandService {
    void processCashback(ProcessCashbackCmd cmd);

    void useCashback(UseCashbackCmd cmd);
}
