package com.fourman.notification.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.notification.domain.UserTopic;

public interface UserTopicDomainRepository extends DomainRepository<UserTopic, UUID> {
    List<String> findTopicEnabled(UUID userId);

    List<UserTopic> findByUserId(UUID userId);

    List<UUID> getUserIdsByTopic(String topic);
}
