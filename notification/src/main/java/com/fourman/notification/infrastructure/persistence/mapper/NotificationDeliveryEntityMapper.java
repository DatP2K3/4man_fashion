package com.fourman.notification.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.notification.domain.NotificationDelivery;
import com.fourman.notification.infrastructure.persistence.entity.NotificationDeliveryEntity;

@Mapper(componentModel = "Spring")
public interface NotificationDeliveryEntityMapper
        extends EntityMapper<NotificationDelivery, NotificationDeliveryEntity> {}
