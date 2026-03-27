package com.fourman.notification.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.notification.domain.Notification;
import com.fourman.notification.infrastructure.persistence.entity.NotificationEntity;

@Mapper(componentModel = "Spring")
public interface NotificationEntityMapper extends EntityMapper<Notification, NotificationEntity> {}
