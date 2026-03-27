package com.fourman.profile.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.event.ProcessCashbackEvent;
import com.fourman.common.dto.event.UseCashbackEvent;
import com.fourman.profile.application.mapper.CommandMapper;
import com.fourman.profile.application.service.CashbackCommandService;
import com.fourman.profile.domain.command.ProcessCashbackCmd;
import com.fourman.profile.domain.command.UseCashbackCmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashbackEventListener {
    private final CommandMapper commandMapper;
    private final CashbackCommandService cashbackCommandService;

    @RabbitListener(queues = "${rabbitmq.queue.cashback.earn}")
    public void handleOrderUpdated(ProcessCashbackEvent event) {
        ProcessCashbackCmd processCashbackCmd = commandMapper.from(event);
        cashbackCommandService.processCashback(processCashbackCmd);
    }

    @RabbitListener(queues = "${rabbitmq.queue.cashback.use}")
    public void handleUseCashback(UseCashbackEvent event) {
        UseCashbackCmd useCashbackCmd = commandMapper.from(event);
        cashbackCommandService.useCashback(useCashbackCmd);
    }
}
