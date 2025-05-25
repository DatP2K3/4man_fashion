package com.evo.profile.infrastructure.adapter.rabbitmq;

import com.evo.common.dto.event.ProcessCashbackEvent;
import com.evo.common.dto.event.UseCashbackEvent;
import com.evo.profile.application.mapper.CommandMapper;
import com.evo.profile.application.service.CashbackCommandService;
import com.evo.profile.domain.command.ProcessCashbackCmd;
import com.evo.profile.domain.command.UseCashbackCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

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
