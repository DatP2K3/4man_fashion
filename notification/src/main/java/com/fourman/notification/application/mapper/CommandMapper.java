package com.fourman.notification.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.event.PushNotificationEvent;
import com.fourman.notification.application.dto.request.RegisterOrUpdateDeviceRequest;
import com.fourman.notification.domain.command.RegisterOrUpdateDeviceCmd;
import com.fourman.notification.domain.command.StoreNotificationCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    StoreNotificationCmd from(PushNotificationEvent pushNotificationEvent);

    RegisterOrUpdateDeviceCmd from(RegisterOrUpdateDeviceRequest registerOrUpdateDeviceRequest);
}
