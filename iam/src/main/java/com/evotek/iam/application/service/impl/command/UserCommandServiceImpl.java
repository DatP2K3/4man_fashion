package com.evotek.iam.application.service.impl.command;

import java.util.*;

import com.evo.common.dto.request.SyncProductRequest;
import com.evo.common.enums.*;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.evo.common.dto.event.PushNotificationEvent;
import com.evo.common.dto.event.SendNotificationEvent;
import com.evo.common.dto.event.SyncProductEvent;
import com.evo.common.support.SecurityContextUtils;
import com.evotek.iam.application.dto.mapper.UserDTOMapper;
import com.evotek.iam.application.dto.response.UserDTO;
import com.evotek.iam.application.mapper.CommandMapper;
import com.evotek.iam.application.mapper.SyncMapper;
import com.evotek.iam.application.service.UserCommandService;
import com.evotek.iam.domain.User;
import com.evotek.iam.domain.UserActivityLog;
import com.evotek.iam.domain.command.*;
import com.evotek.iam.domain.repository.RoleDomainRepository;
import com.evotek.iam.domain.repository.UserDomainRepository;
import com.evotek.iam.infrastructure.adapter.google.GoogleService;
import com.evotek.iam.infrastructure.adapter.keycloak.KeycloakService;
import com.evotek.iam.infrastructure.adapter.notification.NotificationService;
import com.evotek.iam.infrastructure.adapter.storage.FileService;
import com.evotek.iam.infrastructure.support.exception.*;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class UserCommandServiceImpl implements UserCommandService {
    private final KeycloakService keycloakService;
    private final CommandMapper commandMapper;
    private final UserDomainRepository userDomainRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleDomainRepository roleDomainRepository;
    private final ErrorNormalizer errorNormalizer;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final FileService fileService;
    private final NotificationService notificationService;
    private final SyncMapper syncMapper;
    private final GoogleService googleService;

    @Value("${auth.keycloak-enabled}")
    private boolean keycloakEnabled;

    @Value("${outbound.identity.client-id}")
    private String clientId;

    @Value("${outbound.identity.client-secret}")
    protected String clientSecret;

    @Value("${outbound.identity.redirect-uri}")
    protected String redirectUri;

    protected String grantType = "authorization_code";

    @Override
    public UserDTO updateMyUser(CreateOrUpdateUserRequest updateUserRequest) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        CreateOrUpdateUserCmd cmd = commandMapper.from(updateUserRequest);
        User user = userDomainRepository.getByUsername(username);
        user.update(cmd);

        WriteLogCmd logCmd = commandMapper.from("Change Avatar");
        UserActivityLog userActivityLog = new UserActivityLog(logCmd);
        user.setUserActivityLog(userActivityLog);

        SyncProductRequest syncProductRequest = syncMapper.from(user);
        SyncProductEvent syncProductEvent = SyncProductEvent.builder()
                .syncProductType(SyncProductType.USER_UPDATED)
                .syncProductRequest(syncProductRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncProductEvent);
        return userDTOMapper.domainModelToDTO(userDomainRepository.save(user));
    }

    @Override
    public void lockUser(String username, boolean enabled) {
        try {
            User user = userDomainRepository.getByUsername(username);
            user.setLocked(!enabled);
            userDomainRepository.save(user);
            keycloakService.lockUser(user.getProviderId(), enabled);
            Map<String, Object> params = SecurityContextUtils.getSecurityContextMap();
            params.put("username", username);
            SendNotificationEvent mailAlert = SendNotificationEvent.builder()
                    .channel(Channel.EMAIL.name())
                    .recipient(user.getEmail())
                    .templateCode(TemplateCode.LOCK_USER_ALERT)
                    .param(params)
                    .build();
            kafkaTemplate.send("notification-group", mailAlert);

            SyncProductRequest syncProductRequest = syncMapper.from(user);
            SyncProductEvent syncProductEvent = SyncProductEvent.builder()
                    .syncProductType(SyncProductType.USER_UPDATED)
                    .syncProductRequest(syncProductRequest)
                    .build();
            kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncProductEvent);
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }

    @Override
    public void testFcm(PushNotificationEvent pushNotificationEvent) {
        kafkaTemplate.send(KafkaTopic.PUSH_NOTIFICATION_GROUP.getTopicName(), pushNotificationEvent);
    }

}
