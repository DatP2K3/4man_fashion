package com.fourman.notification.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fourman.common.support.IdUtils;
import com.fourman.notification.domain.command.RegisterOrUpdateDeviceCmd;
import com.fourman.notification.domain.command.StoreNotificationDeliveryCmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRegistration {
    private UUID id;
    private UUID userId;
    private String deviceToken;
    private UUID deviceId;
    private boolean enabled;
    private List<NotificationDelivery> notificationDeliveries;

    public DeviceRegistration(RegisterOrUpdateDeviceCmd cmd) {
        this.id = IdUtils.nextId();
        this.userId = cmd.getUserId();
        this.deviceToken = cmd.getDeviceToken();
        this.deviceId = cmd.getDeviceId();
        this.enabled = cmd.isEnabled();
    }

    public void addNotificationDelivery(StoreNotificationDeliveryCmd cmd) {
        NotificationDelivery notificationDelivery = new NotificationDelivery(cmd);
        if (this.notificationDeliveries == null) {
            this.notificationDeliveries = new ArrayList<>();
        }
        this.notificationDeliveries.add(notificationDelivery);
    }
}
