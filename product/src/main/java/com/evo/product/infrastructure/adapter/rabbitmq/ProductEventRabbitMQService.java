package com.evo.product.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.ProductEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventRabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.product}")
    private String productExchange;

    @Value("${rabbitmq.routing.key.product.create}")
    private String productCreateRoutingKey;

    @Value("${rabbitmq.routing.key.product.update}")
    private String productUpdateRoutingKey;

    @Value("${rabbitmq.routing.key.product.delete}")
    private String productDeleteRoutingKey;

    @Value("${rabbitmq.routing.key.product.update-all}")
    private String productUpdateAllRoutingKey;

    public void publishProductCreatedEvent(ProductEvent event) {
        log.info("Sending product CREATED event: {}", event);
        rabbitTemplate.convertAndSend(productExchange, productCreateRoutingKey, event);
    }

    public void publishProductUpdatedEvent(ProductEvent event) {
        log.info("Sending product UPDATED event: {}", event);
        rabbitTemplate.convertAndSend(productExchange, productUpdateRoutingKey, event);
    }

    public void publishUpdateAllProductEvent(ProductEvent event) {
        log.info("Sending product UPDATE-ALL event: {}", event);
        rabbitTemplate.convertAndSend(productExchange, productUpdateAllRoutingKey, event);
    }

    public void publishProductDeletedEvent(ProductEvent event) {
        log.info("Sending product DELETED event: {}", event);
        rabbitTemplate.convertAndSend(productExchange, productDeleteRoutingKey, event);
    }
}
