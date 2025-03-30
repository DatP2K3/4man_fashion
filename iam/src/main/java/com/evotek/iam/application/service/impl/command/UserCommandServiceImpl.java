package com.evotek.iam.application.service.impl.command;

import java.io.IOException;
import java.util.*;

import com.evo.common.enums.*;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.event.PushNotificationEvent;
import com.evo.common.dto.event.SendNotificationEvent;
import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.dto.request.SyncUserRequest;
import com.evo.common.support.SecurityContextUtils;
import com.evotek.iam.application.dto.mapper.UserDTOMapper;
import com.evotek.iam.application.dto.request.ChangePasswordRequest;
import com.evotek.iam.application.dto.request.ExchangeTokenRequest;
import com.evotek.iam.application.dto.response.OutboundUserDTO;
import com.evotek.iam.application.dto.response.TokenDTO;
import com.evotek.iam.application.dto.response.UserDTO;
import com.evotek.iam.application.mapper.CommandMapper;
import com.evotek.iam.application.mapper.SyncMapper;
import com.evotek.iam.application.service.UserCommandService;
import com.evotek.iam.domain.Role;
import com.evotek.iam.domain.User;
import com.evotek.iam.domain.UserActivityLog;
import com.evotek.iam.domain.UserRole;
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
    private final PasswordEncoder passwordEncoder;
    private final UserDomainRepository userDomainRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleDomainRepository roleDomainRepository;
    private final ErrorNormalizer errorNormalizer;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final FileService fileService;
    private final NotificationService notificationService;
    private final SyncMapper syncMapper;
    private final GoogleService googleService;
    private final SelfIDPAuthCommandServiceImpl selfIDPAuthCommandService;

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
    public UserDTO createDefaultUser(CreateOrUpdateUserRequest request) {
        try {
            CreateOrUpdateUserCmd createOrUpdateUserCmd = commandMapper.from(request);

            if (request.getProvider() == null) {
                if (keycloakEnabled) {
                    createOrUpdateUserCmd.setProvider("keycloak");
                } else {
                    createOrUpdateUserCmd.setProvider("self_idp");
                }
                createOrUpdateUserCmd.setProviderId(UUID.fromString(keycloakService.createKeycloakUser(request)));
                createOrUpdateUserCmd.setPassword(passwordEncoder.encode(createOrUpdateUserCmd.getPassword()));
            } else {
                createOrUpdateUserCmd.setProvider(request.getProvider());
            }
            Role role = roleDomainRepository.findByName("ROLE_USER");
            User user = new User(createOrUpdateUserCmd);
            UserRole userRole = new UserRole(role.getId(), user.getId());
            user.setUserRoles(Collections.singletonList(userRole));
            user = userDomainRepository.save(user);

            notificationService.initUserTopic(user.getId());

            Map<String, Object> params = SecurityContextUtils.getSecurityContextMap();
            params.put("username", user.getUsername());
            params.put("email", user.getEmail());
            SendNotificationEvent mailAlert = SendNotificationEvent.builder()
                    .channel(Channel.EMAIL.name())
                    .recipient(user.getEmail())
                    .templateCode(TemplateCode.OTP_ALERT)
                    .param(params)
                    .build();
            kafkaTemplate.send(KafkaTopic.SEND_NOTIFICATION_GROUP.getTopicName(), mailAlert);

            SyncUserRequest syncUserRequest = syncMapper.from(user);
            SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                    .syncUserType(SyncUserType.USER_CREATED)
                    .syncUserRequest(syncUserRequest)
                    .build();
            kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncUserEvent);

            return userDTOMapper.domainModelToDTO(user);
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }

    @Override
    public UserDTO createUser(CreateOrUpdateUserRequest request) {
        UUID keycloakUserId = UUID.fromString(keycloakService.createKeycloakUser(request));

        CreateOrUpdateUserCmd createOrUpdateUserCmd = commandMapper.from(request);
        createOrUpdateUserCmd.setPassword(passwordEncoder.encode(createOrUpdateUserCmd.getPassword()));

        if (keycloakEnabled) {
            createOrUpdateUserCmd.setProvider("keycloak");
        } else {
            createOrUpdateUserCmd.setProvider("self_idp");
        }
        createOrUpdateUserCmd.setProviderId(keycloakUserId);
        User user = new User(createOrUpdateUserCmd);
        userDomainRepository.save(user);

        Map<String, Object> params = SecurityContextUtils.getSecurityContextMap();
        params.put("username", user.getUsername());
        params.put("email", user.getEmail());
        SendNotificationEvent mailAlert = SendNotificationEvent.builder()
                .channel(Channel.EMAIL.name())
                .recipient(user.getEmail())
                .templateCode(TemplateCode.SIGNIN_ALERT)
                .param(params)
                .build();
        kafkaTemplate.send(KafkaTopic.SEND_NOTIFICATION_GROUP.getTopicName(), mailAlert);

        SyncUserRequest syncUserRequest = syncMapper.from(user);
        SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                .syncUserType(SyncUserType.USER_CREATED)
                .syncUserRequest(syncUserRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncUserEvent);

        return userDTOMapper.domainModelToDTO(user);
    }

    @Override
    public void OverwritePassword(String username, ChangePasswordRequest request) {
        User user = userDomainRepository.getByUsername(username);
        UUID keycloakUserId = user.getProviderId();

        ResetKeycloakPasswordCmd resetKeycloakPasswordCmd = ResetKeycloakPasswordCmd.builder()
                .value(request.getNewPassword())
                .build();
        keycloakService.resetPassword(keycloakUserId, resetKeycloakPasswordCmd);

        user.changePassword(passwordEncoder.encode(request.getNewPassword()));
        WriteLogCmd logCmd = commandMapper.from("Change Password");
        UserActivityLog userActivityLog = new UserActivityLog(logCmd);
        user.setUserActivityLog(userActivityLog);
        userDomainRepository.save(user);

        Map<String, Object> params = SecurityContextUtils.getSecurityContextMap();
        SendNotificationEvent mailAlert = SendNotificationEvent.builder()
                .channel(Channel.EMAIL.name())
                .recipient(user.getEmail())
                .templateCode(TemplateCode.PASSWORD_CHANGE_ALERT)
                .param(params)
                .build();
        kafkaTemplate.send(KafkaTopic.SEND_NOTIFICATION_GROUP.getTopicName(), mailAlert);
    }

    @Override
    public void changeMyPassword(ChangePasswordRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        ChangePasswordCmd changePasswordCmd = commandMapper.from(request);

        User user = userDomainRepository.getByUsername(username);
        UUID keycloakUserId = user.getProviderId();

        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            ResetKeycloakPasswordCmd resetKeycloakPasswordCmd = ResetKeycloakPasswordCmd.builder()
                    .value(changePasswordCmd.getNewPassword())
                    .build();
            keycloakService.resetPassword(keycloakUserId, resetKeycloakPasswordCmd);

            user.changePassword(passwordEncoder.encode(request.getNewPassword()));
            WriteLogCmd logCmd = commandMapper.from("Change Password");
            UserActivityLog userActivityLog = new UserActivityLog(logCmd);
            user.setUserActivityLog(userActivityLog);
            userDomainRepository.save(user);

            Map<String, Object> params = SecurityContextUtils.getSecurityContextMap();
            SendNotificationEvent mailAlert = SendNotificationEvent.builder()
                    .channel(Channel.EMAIL.name())
                    .recipient(user.getEmail())
                    .templateCode(TemplateCode.PASSWORD_CHANGE_ALERT)
                    .param(params)
                    .build();
            kafkaTemplate.send(KafkaTopic.SEND_NOTIFICATION_GROUP.getTopicName(), mailAlert);
        } else {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
        }
    }

    @Override
    public List<UserDTO> importUserFile(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header row
            rowIterator.next(); // Skip another row if needed

            List<UserDTO> userDTOS = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                CreateOrUpdateUserRequest.CreateOrUpdateUserRequestBuilder builder = CreateOrUpdateUserRequest.builder();
                List<String> errors = new ArrayList<>();
                int rowIndex = row.getRowNum() + 1;

                processUsernameCell(row.getCell(0), rowIndex, builder, errors);
                processPasswordCell(row.getCell(1), rowIndex, builder, errors);
                processEmailCell(row.getCell(2), rowIndex, builder, errors);

                if (!errors.isEmpty()) {
                    logErrors(errors);
                } else {
                    CreateOrUpdateUserRequest request = builder.build();
                    userDTOS.add(createDefaultUser(request));
                }
            }
            return userDTOS;
        } catch (IOException e) {
            throw new AppException(AppErrorCode.FILE_NOT_FOUND);
        }
    }

    private void processUsernameCell(
            Cell cell, int rowIndex, CreateOrUpdateUserRequest.CreateOrUpdateUserRequestBuilder builder, List<String> errors) {
        if (cell != null) {
            String username = cell.getStringCellValue().trim();
            if (username.isEmpty()) {
                errors.add("Dòng " + rowIndex + ": Username bị trống.");
            } else if (userDomainRepository.existsByUsername(username)) {
                errors.add("Dòng " + rowIndex + ": Username '" + username + "' đã tồn tại.");
            } else {
                builder.username(username);
            }
        } else {
            errors.add("Dòng " + rowIndex + ": Username bị trống.");
        }
    }

    private void processPasswordCell(
            Cell cell, int rowIndex, CreateOrUpdateUserRequest.CreateOrUpdateUserRequestBuilder builder, List<String> errors) {
        if (cell != null) {
            String password = cell.getStringCellValue().trim();
            if (password.isEmpty()) {
                errors.add("Dòng " + rowIndex + ": Password bị trống.");
            } else {
                builder.password(passwordEncoder.encode(password));
            }
        } else {
            errors.add("Dòng " + rowIndex + ": Password bị trống.");
        }
    }

    private void processEmailCell(
            Cell cell, int rowIndex, CreateOrUpdateUserRequest.CreateOrUpdateUserRequestBuilder builder, List<String> errors) {
        if (cell != null) {
            String email = cell.getStringCellValue().trim();
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                errors.add("Dòng " + rowIndex + ": Email không hợp lệ.");
            } else {
                builder.email(email);
            }
        }
    }

    private void logErrors(List<String> errors) {
        for (String error : errors) {
            log.warn(error);
        }
    }

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

        SyncUserRequest syncUserRequest = syncMapper.from(user);
        SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                .syncUserType(SyncUserType.USER_UPDATED)
                .syncUserRequest(syncUserRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncUserEvent);
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

            SyncUserRequest syncUserRequest = syncMapper.from(user);
            SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                    .syncUserType(SyncUserType.USER_UPDATED)
                    .syncUserRequest(syncUserRequest)
                    .build();
            kafkaTemplate.send(KafkaTopic.SYNC_USER_PROFILE_GROUP.getTopicName(), syncUserEvent);
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
    }

    @Override
    public void testFcm(PushNotificationEvent pushNotificationEvent) {
        kafkaTemplate.send(KafkaTopic.PUSH_NOTIFICATION_GROUP.getTopicName(), pushNotificationEvent);
    }

    @Override
    public TokenDTO outboundAuthenticate(String code) {
        ExchangeTokenRequest exchangeTokenRequest = ExchangeTokenRequest.builder()
                .code(code)
                .redirectUri(redirectUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(grantType)
                .build();

        TokenDTO tokenDTO = googleService.exchangeToken(exchangeTokenRequest);

        OutboundUserDTO outboundUserDTO = googleService.getUserInfo(tokenDTO.getAccessToken());

        boolean isUserExist = userDomainRepository.existsByUsername(outboundUserDTO.getEmail());

        if (!isUserExist) {
            CreateOrUpdateUserRequest createOrUpdateUserRequest = CreateOrUpdateUserRequest.builder()
                    .username(outboundUserDTO.getEmail())
                    .email(outboundUserDTO.getEmail())
                    .provider("google")
                    .providerId(UUID.nameUUIDFromBytes(
                            String.valueOf(outboundUserDTO.getSub()).getBytes()))
                    .build();

            UserDTO userDTO = createDefaultUser(createOrUpdateUserRequest);

            User user = userDTOMapper.dtoToDomainModel(userDTO);

            var accessToken = selfIDPAuthCommandService.generateToken(user, false, false);
            var refreshToken = selfIDPAuthCommandService.generateToken(user, false, true);
            return TokenDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            User user = userDomainRepository.getByUsername(outboundUserDTO.getEmail());
            var accessToken = selfIDPAuthCommandService.generateToken(user, false, false);
            var refreshToken = selfIDPAuthCommandService.generateToken(user, false, true);
            return TokenDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }

    @Override
    public Boolean refreshScope() {
        return keycloakEnabled;
    }
}
