package com.evo.order.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.PushNotificationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotiEventRabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.noti}")
    private String notiExchange;

    @Value("${rabbitmq.routing.key.noti.push}")
    private String notiPushRoutingKey;

    public void publishNotiPushEvent(PushNotificationEvent event) {
        rabbitTemplate.convertAndSend(notiExchange, notiPushRoutingKey, event);
    }
}
