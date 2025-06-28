package com.evo.order.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.OrderEvent;
import com.evo.order.application.mapper.CommandMapper;
import com.evo.order.application.service.OrderCommandService;
import com.evo.order.domain.command.UpdateOrderStatusCmd;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    private final CommandMapper commandMapper;
    private final OrderCommandService orderCommandService;

    @RabbitListener(queues = "${rabbitmq.queue.order.update}")
    public void handleOrderUpdated(OrderEvent event) {
        UpdateOrderStatusCmd updateOrderStatusCmd = commandMapper.from(event);
        orderCommandService.updateStatus(updateOrderStatusCmd);
    }
}
