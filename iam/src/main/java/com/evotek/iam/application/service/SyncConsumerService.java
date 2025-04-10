package com.evotek.iam.application.service;

import com.evo.common.dto.event.SyncProductEvent;
import com.evo.common.dto.request.SyncProductRequest;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import com.evotek.iam.application.mapper.SyncMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncConsumerService {
    private final UserCommandService userCommandService;
private final SyncMapper syncMapper;

    @KafkaListener(topics = "sync-user-profile-group")
    public void syncUser(SyncProductEvent event) {
        SyncProductRequest syncProductRequest = event.getSyncProductRequest();
        CreateOrUpdateUserRequest request = syncMapper.from(syncProductRequest);

        switch (event.getSyncProductType()) {
            case USER_UPDATED:
                userCommandService.updateMyUser(request);
                break;
            default:
                log.error("Invalid sync user type: {}", event.getSyncProductType());
        }
    }
}
