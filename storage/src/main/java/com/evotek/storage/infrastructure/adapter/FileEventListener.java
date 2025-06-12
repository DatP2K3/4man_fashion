package com.evotek.storage.infrastructure.adapter;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.FileEvent;
import com.evotek.storage.application.mapper.CommandMapper;
import com.evotek.storage.application.service.FileCommandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileEventListener {
    private final CommandMapper commandMapper;
    private final FileCommandService fileCommandService;

    @RabbitListener(queues = "${rabbitmq.queue.file.update}")
    public void handleProductUpdated(FileEvent event) {
        fileCommandService.updateFileStatus(event);
    }
}
