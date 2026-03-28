package com.fourman.notification.infrastructure.domainrepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.notification.domain.Notification;
import com.fourman.notification.domain.exception.NotFoundError;
import com.fourman.notification.domain.repository.NotificationDomainRepository;
import com.fourman.notification.infrastructure.persistence.entity.NotificationEntity;
import com.fourman.notification.infrastructure.persistence.mapper.NotificationEntityMapper;
import com.fourman.notification.infrastructure.persistence.repository.NotificationEntityRepository;

@Repository
public class NotificationDomainRepositoryImpl extends AbstractDomainRepository<Notification, NotificationEntity, UUID>
        implements NotificationDomainRepository {
    private final NotificationEntityMapper notificationEntityMapper;
    private final NotificationEntityRepository notificationEntityRepository;

    public NotificationDomainRepositoryImpl(
            NotificationEntityMapper notificationEntityMapper,
            NotificationEntityRepository notificationEntityRepository) {
        super(notificationEntityRepository, notificationEntityMapper);
        this.notificationEntityMapper = notificationEntityMapper;
        this.notificationEntityRepository = notificationEntityRepository;
    }

    @Override
    public Notification getById(UUID uuid) {
        NotificationEntity notificationEntity = notificationEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new ResponseException(NotFoundError.NOTIFICATION_NOT_FOUND));
        return notificationEntityMapper.toDomainModel(notificationEntity);
    }
}
