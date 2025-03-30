package com.evo.profile.application.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.dto.request.SyncUserRequest;
import com.evo.profile.application.dto.request.CreateOrUpdateProfileRequest;
import com.evo.profile.application.mapper.SyncMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncConsumerService {
    private final ProfileCommandService profileCommandService;
    private final SyncMapper syncMapper;

    @KafkaListener(topics = "sync-user-profile-group")
    public void syncUser(SyncUserEvent event) {
        SyncUserRequest syncUserRequest = event.getSyncUserRequest();
        CreateOrUpdateProfileRequest request = syncMapper.from(syncUserRequest);

        switch (event.getSyncUserType()) {
            case USER_CREATED:
                profileCommandService.syncCreate(request);
                break;
            case USER_UPDATED:
                profileCommandService.syncUpdate(request);
                break;
            case USER_DELETED:
                profileCommandService.delete(syncUserRequest.getId(), syncUserRequest.isLocked());
                break;
            default:
                log.error("Invalid sync user type: {}", event.getSyncUserType());
        }
    }
}
