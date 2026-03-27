package com.fourman.notification.domain.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.notification.domain.DeviceRegistration;

public interface DeviceRegistrationDomainRepository extends DomainRepository<DeviceRegistration, UUID> {
    List<DeviceRegistration> findByUserIdAndEnabled(UUID userId);

    List<DeviceRegistration> findByDeviceTokenAndEnabled(String deviceToken);

    DeviceRegistration findByDeviceIdAndUserId(UUID deviceId, UUID userId);

    List<String> getDeviceTokensByUserId(UUID userId);

    List<DeviceRegistration> findByUserIdInAndEnabledTrue(List<UUID> userIds);

    List<DeviceRegistration> findInactivatedDevices(Instant cutoffDate);

    void hardDeleteDeviceRegistration(List<DeviceRegistration> deviceRegistrations);
}
