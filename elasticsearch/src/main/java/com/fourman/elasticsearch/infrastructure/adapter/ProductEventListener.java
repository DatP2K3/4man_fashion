package com.fourman.elasticsearch.infrastructure.adapter;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fourman.common.dto.event.ProductEvent;
import com.fourman.elasticsearch.application.mapper.CommandMapper;
import com.fourman.elasticsearch.application.service.ProductCommandService;
import com.fourman.elasticsearch.domain.command.SyncProductCmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {
    private final CommandMapper commandMapper;
    private final ProductCommandService productCommandService;

    @RabbitListener(queues = "${rabbitmq.queue.product.create}")
    public void handleProductCreated(ProductEvent event) {
        SyncProductCmd syncProductCmd =
                commandMapper.from(event.getProductSyncs().getFirst());
        productCommandService.create(syncProductCmd);
    }

    @RabbitListener(queues = "${rabbitmq.queue.product.update}")
    public void handleProductUpdated(ProductEvent event) {
        SyncProductCmd syncProductCmd =
                commandMapper.from(event.getProductSyncs().getFirst());
        productCommandService.update(syncProductCmd);
    }

    @RabbitListener(queues = "${rabbitmq.queue.product.update-all}")
    public void handleProductUpdatedAll(ProductEvent event) {
        event.getProductSyncs().forEach(syncProduct -> {
            productCommandService.update(commandMapper.from(syncProduct));
        });
    }

    @RabbitListener(queues = "${rabbitmq.queue.product.delete}")
    public void handleProductDeleted(ProductEvent event) {}
}
