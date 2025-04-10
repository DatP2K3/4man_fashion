package com.evotek.elasticsearch.application.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.SyncProductEvent;
import com.evotek.elasticsearch.application.mapper.CommandMapper;
import com.evotek.elasticsearch.application.service.impl.command.ProductCommandServiceImpl;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncConsumerService {
    private final ProductCommandServiceImpl userCommandServiceImpl;
    private final CommandMapper commandMapper;

    @KafkaListener(topics = "sync-product-group")
    public void syncProduct(SyncProductEvent event) {
        SyncProductCmd syncProductCmd = commandMapper.from(event.getSyncProductRequest());
        switch (event.getSyncProductType()) {
            case PRODUCT_CREATED:
                userCommandServiceImpl.create(syncProductCmd);
                break;
            case PRODUCT_UPDATED:
                userCommandServiceImpl.update(syncProductCmd);
                break;
            case PRODUCT_DELETED:
                userCommandServiceImpl.delete(syncProductCmd.getId());
                break;
            default:
                log.error("Invalid sync product type: {}", event.getSyncProductType());
        }
    }
}
