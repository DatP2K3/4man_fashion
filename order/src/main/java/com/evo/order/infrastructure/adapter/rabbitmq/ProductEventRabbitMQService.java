package com.evo.order.infrastructure.adapter.rabbitmq;

import com.evo.common.dto.event.ProductVariantEvent;
import com.evo.common.dto.event.PushNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventRabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.product}")
    private String productExchange;

    @Value("${rabbitmq.routing.key.product.update-variant}")
    private String productPushRoutingKey;

    public void publishProductPushEvent(ProductVariantEvent event) {
        rabbitTemplate.convertAndSend(productExchange, productPushRoutingKey, event);
    }
}
