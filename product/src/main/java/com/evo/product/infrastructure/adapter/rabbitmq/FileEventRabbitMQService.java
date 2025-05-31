package com.evo.product.infrastructure.adapter.rabbitmq;

import com.evo.common.dto.event.FileEvent;
import com.evo.common.dto.event.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileEventRabbitMQService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.file}")
    private String fileExchange;

    @Value("${rabbitmq.routing.key.file.update}")
    private String fileUpdateRoutingKey;

    public void publishFileUpdatedEvent(FileEvent event) {
        log.info("Sending file UPDATE event: {}", event);
        rabbitTemplate.convertAndSend(fileExchange, fileUpdateRoutingKey, event);
    }
}
