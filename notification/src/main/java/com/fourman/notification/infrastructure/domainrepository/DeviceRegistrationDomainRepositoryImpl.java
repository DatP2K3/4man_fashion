package com.fourman.notification.infrastructure.domainrepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.notification.domain.DeviceRegistration;
import com.fourman.notification.domain.NotificationDelivery;
import com.fourman.notification.domain.exception.NotFoundError;
import com.fourman.notification.domain.repository.DeviceRegistrationDomainRepository;
import com.fourman.notification.infrastructure.persistence.entity.DeviceRegistrationEntity;
import com.fourman.notification.infrastructure.persistence.entity.NotificationDeliveryEntity;
import com.fourman.notification.infrastructure.persistence.mapper.DeviceRegistrationEntityMapper;
import com.fourman.notification.infrastructure.persistence.mapper.NotificationDeliveryEntityMapper;
import com.fourman.notification.infrastructure.persistence.repository.DeviceRegistrationEntityRepository;
import com.fourman.notification.infrastructure.persistence.repository.NotificationDeliveryEntityRepository;

@Repository
public class DeviceRegistrationDomainRepositoryImpl
        extends AbstractDomainRepository<DeviceRegistration, DeviceRegistrationEntity, UUID>
        implements DeviceRegistrationDomainRepository {
    private final DeviceRegistrationEntityRepository deviceRegistrationEntityRepository;
    private final DeviceRegistrationEntityMapper deviceRegistrationEntityMapper;
    private final NotificationDeliveryEntityRepository notificationDeliveryEntityRepository;
    private final NotificationDeliveryEntityMapper notificationDeliveryEntityMapper;

    public DeviceRegistrationDomainRepositoryImpl(
            DeviceRegistrationEntityRepository deviceRegistrationEntityRepository,
            DeviceRegistrationEntityMapper deviceRegistrationEntityMapper,
            NotificationDeliveryEntityRepository notificationDeliveryEntityRepository,
            NotificationDeliveryEntityMapper notificationDeliveryEntityMapper) {
        super(deviceRegistrationEntityRepository, deviceRegistrationEntityMapper);
        this.deviceRegistrationEntityRepository = deviceRegistrationEntityRepository;
        this.deviceRegistrationEntityMapper = deviceRegistrationEntityMapper;
        this.notificationDeliveryEntityRepository = notificationDeliveryEntityRepository;
        this.notificationDeliveryEntityMapper = notificationDeliveryEntityMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeviceRegistration save(DeviceRegistration deviceRegistration) {

        DeviceRegistrationEntity deviceRegistrationEntity = deviceRegistrationEntityMapper.toEntity(deviceRegistration);
        List<NotificationDelivery> notificationDeliveries = deviceRegistration.getNotificationDeliveries();
        List<NotificationDeliveryEntity> notificationDeliveryEntities =
                notificationDeliveryEntityMapper.toEntityList(notificationDeliveries);
        if (notificationDeliveryEntities != null && !notificationDeliveryEntities.isEmpty()) {
            notificationDeliveryEntityRepository.saveAll(notificationDeliveryEntities);
        }
        deviceRegistrationEntity = deviceRegistrationEntityRepository.save(deviceRegistrationEntity);
        return this.enrich(deviceRegistrationEntityMapper.toDomainModel(deviceRegistrationEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeviceRegistration> saveAll(List<DeviceRegistration> deviceRegistrations) {
        // 1. Collect all NotificationDeliveries from all devices
        List<NotificationDelivery> allDeliveries = deviceRegistrations.stream()
                .map(DeviceRegistration::getNotificationDeliveries)
                .filter(deliveries -> deliveries != null && !deliveries.isEmpty())
                .flatMap(List::stream)
                .toList();

        // 2. Batch save all NotificationDeliveries in one query
        if (!allDeliveries.isEmpty()) {
            List<NotificationDeliveryEntity> deliveryEntities =
                    notificationDeliveryEntityMapper.toEntityList(allDeliveries);
            notificationDeliveryEntityRepository.saveAll(deliveryEntities);
        }

        // 3. Batch save all DeviceRegistrations in one query
        List<DeviceRegistrationEntity> entities = deviceRegistrationEntityMapper.toEntityList(deviceRegistrations);
        entities = deviceRegistrationEntityRepository.saveAll(entities);

        return this.enrichList(deviceRegistrationEntityMapper.toDomainModelList(entities));
    }

    @Override
    public DeviceRegistration getById(UUID uuid) {
        DeviceRegistrationEntity deviceRegistrationEntity = deviceRegistrationEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new ResponseException(NotFoundError.DEVICE_REGISTRATION_NOT_FOUND));
        return this.enrich(deviceRegistrationEntityMapper.toDomainModel(deviceRegistrationEntity));
    }

    @Override
    public List<DeviceRegistration> findByUserIdAndEnabled(UUID userId) {
        List<DeviceRegistrationEntity> deviceRegistrationEntities =
                deviceRegistrationEntityRepository.findByUserIdAndEnabledTrue(userId);
        return this.enrichList(deviceRegistrationEntityMapper.toDomainModelList(deviceRegistrationEntities));
    }

    @Override
    public List<DeviceRegistration> findByDeviceTokenAndEnabled(String deviceToken) {
        List<DeviceRegistrationEntity> deviceRegistrationEntities =
                deviceRegistrationEntityRepository.findByDeviceTokenAndEnabledTrue(deviceToken);
        return this.enrichList(deviceRegistrationEntityMapper.toDomainModelList(deviceRegistrationEntities));
    }

    @Override
    public DeviceRegistration findByDeviceIdAndUserId(UUID deviceId, UUID userId) {
        DeviceRegistrationEntity deviceRegistrationEntity = deviceRegistrationEntityRepository
                .findByDeviceIdAndUserId(deviceId, userId)
                .orElse(null);
        if (deviceRegistrationEntity != null) {
            return this.enrich(deviceRegistrationEntityMapper.toDomainModel(deviceRegistrationEntity));
        }
        return null;
    }

    @Override
    public List<String> getDeviceTokensByUserId(UUID userId) {
        return deviceRegistrationEntityRepository.findDeviceTokenByUserId(userId);
    }

    @Override
    public List<DeviceRegistration> findByUserIdInAndEnabledTrue(List<UUID> userIds) {
        List<DeviceRegistrationEntity> deviceRegistrationEntities =
                deviceRegistrationEntityRepository.findByUserIdInAndEnabledTrue(userIds);
        return this.enrichList(deviceRegistrationEntityMapper.toDomainModelList(deviceRegistrationEntities));
    }

    @Override
    public List<DeviceRegistration> findInactivatedDevices(Instant cutoffDate) {
        List<DeviceRegistrationEntity> deviceRegistrationEntities =
                deviceRegistrationEntityRepository.findInactivatedDevices(cutoffDate);
        return this.enrichList(deviceRegistrationEntityMapper.toDomainModelList(deviceRegistrationEntities));
    }

    @Override
    public void hardDeleteDeviceRegistration(List<DeviceRegistration> deviceRegistrations) {
        List<DeviceRegistrationEntity> deviceRegistrationEntities =
                deviceRegistrationEntityMapper.toEntityList(deviceRegistrations);
        deviceRegistrationEntityRepository.deleteAll(deviceRegistrationEntities);
    }

    @Override
    protected List<DeviceRegistration> enrichList(List<DeviceRegistration> deviceRegistrations) {
        if (deviceRegistrations.isEmpty()) return deviceRegistrations;

        List<UUID> deviceIds =
                deviceRegistrations.stream().map(DeviceRegistration::getId).toList();
        Map<UUID, List<NotificationDelivery>> deviceMap =
                notificationDeliveryEntityRepository.findByDeviceRegistrationIdIn(deviceIds).stream()
                        .collect(Collectors.groupingBy(
                                NotificationDeliveryEntity::getDeviceRegistrationId,
                                Collectors.mapping(
                                        notificationDeliveryEntityMapper::toDomainModel, Collectors.toList())));

        deviceRegistrations.forEach(deviceRegistration -> deviceRegistration.setNotificationDeliveries(
                new ArrayList<>(deviceMap.getOrDefault(deviceRegistration.getId(), Collections.emptyList()))));
        return deviceRegistrations;
    }
}
