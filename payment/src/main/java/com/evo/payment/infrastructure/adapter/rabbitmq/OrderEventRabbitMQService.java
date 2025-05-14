package com.evo.payment.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.OrderEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventRabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.product}")
    private String orderExchange;

    @Value("${rabbitmq.routing.key.order.update}")
    private String orderUpdateRoutingKey;

    public void publishOrderUpdateEvent(OrderEvent event) {
        log.info("Sending product CREATED event: {}", event);
        rabbitTemplate.convertAndSend(orderExchange, orderUpdateRoutingKey, event);
    }
}
