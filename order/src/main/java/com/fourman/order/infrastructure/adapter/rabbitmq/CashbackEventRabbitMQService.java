package com.fourman.order.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.event.ProcessCashbackEvent;
import com.fourman.common.dto.event.UseCashbackEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashbackEventRabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.cashback}")
    private String cashbackExchange;

    @Value("${rabbitmq.routing.key.cashback.earn}")
    private String cashbackEarnRoutingKey;

    @Value("${rabbitmq.routing.key.cashback.use}")
    private String cashbackUseRoutingKey;

    public void publishProcessCashbackEvent(ProcessCashbackEvent event) {
        rabbitTemplate.convertAndSend(cashbackExchange, cashbackEarnRoutingKey, event);
    }

    public void publishUseCashbackEvent(UseCashbackEvent event) {
        rabbitTemplate.convertAndSend(cashbackExchange, cashbackUseRoutingKey, event);
    }
}
