package com.evotek.notification.infrastructure.adapter.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.PushNotificationEvent;
import com.evotek.notification.application.service.NotificationConsumerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotiEventListener {
    private final NotificationConsumerService notificationConsumerService;

    @RabbitListener(queues = "${rabbitmq.queue.noti.push}")
    public void handleNotiPush(PushNotificationEvent event) {
        notificationConsumerService.handlePushNotification(event);
    }
}
