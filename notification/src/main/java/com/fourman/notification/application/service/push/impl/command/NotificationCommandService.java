package com.fourman.notification.application.service.push.impl.command;

import org.springframework.stereotype.Service;

import com.fourman.common.dto.event.PushNotificationEvent;
import com.fourman.notification.application.mapper.CommandMapper;
import com.fourman.notification.domain.Notification;
import com.fourman.notification.domain.command.StoreNotificationCmd;
import com.fourman.notification.domain.repository.NotificationDomainRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationCommandService {
    private final NotificationDomainRepository notificationDomainRepository;
    private final CommandMapper commandMapper;

    public Notification storeNotification(PushNotificationEvent request) {
        StoreNotificationCmd storeNotificationCmd = commandMapper.from(request);
        Notification notification = new Notification(storeNotificationCmd);
        return notificationDomainRepository.save(notification);
    }
}
