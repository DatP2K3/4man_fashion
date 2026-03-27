package com.fourman.notification.domain.repository;

import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.notification.domain.Notification;

public interface NotificationDomainRepository extends DomainRepository<Notification, UUID> {}
