package com.fourman.notification.application.service.push.impl.command;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fourman.common.exception.ResponseException;
import com.fourman.notification.application.dto.request.RegisterOrUpdateDeviceRequest;
import com.fourman.notification.application.dto.request.UnRegisterDeviceRequest;
import com.fourman.notification.application.mapper.CommandMapper;
import com.fourman.notification.domain.DeviceRegistration;
import com.fourman.notification.domain.command.RegisterOrUpdateDeviceCmd;
import com.fourman.notification.domain.repository.DeviceRegistrationDomainRepository;
import com.fourman.notification.domain.repository.UserTopicDomainRepository;
import com.fourman.notification.infrastructure.support.exception.BadRequestError;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceRegistrationCommandService {
    private final DeviceRegistrationDomainRepository deviceRegistrationDomainRepository;
    private final CommandMapper commandMapper;
    private final UserTopicDomainRepository userTopicDomainRepository;

    public void registerDevice(RegisterOrUpdateDeviceRequest request) {
        RegisterOrUpdateDeviceCmd registerOrUpdateDeviceCmd = commandMapper.from(request);

        DeviceRegistration existingDeviceRegistration =
                deviceRegistrationDomainRepository.findByDeviceIdAndUserId(request.getDeviceId(), request.getUserId());
        if (existingDeviceRegistration != null) {
            existingDeviceRegistration.setEnabled(true);
            subscribeTokenTopic(registerOrUpdateDeviceCmd.getDeviceToken(), registerOrUpdateDeviceCmd.getUserId());
            deviceRegistrationDomainRepository.save(existingDeviceRegistration);
        } else {
            DeviceRegistration deviceRegistration = new DeviceRegistration(registerOrUpdateDeviceCmd);
            subscribeTokenTopic(registerOrUpdateDeviceCmd.getDeviceToken(), registerOrUpdateDeviceCmd.getUserId());
            deviceRegistrationDomainRepository.save(deviceRegistration);
        }
    }

    public void unregisterDevice(UnRegisterDeviceRequest unRegisterDeviceRequest) {
        DeviceRegistration deviceRegistration = deviceRegistrationDomainRepository.findByDeviceIdAndUserId(
                unRegisterDeviceRequest.getDeviceId(), unRegisterDeviceRequest.getUserId());
        if (deviceRegistration != null) {
            deviceRegistration.setEnabled(false);
            deviceRegistrationDomainRepository.save(deviceRegistration);
            List<String> topics = userTopicDomainRepository.findTopicEnabled(deviceRegistration.getUserId());
            unsubscribeDeviceFromTopic(unRegisterDeviceRequest, topics);
        }
    }

    public void subscribeTokenTopic(String deviceToken, UUID userId) {
        List<String> fcmTopics = userTopicDomainRepository.findTopicEnabled(userId);
        fcmTopics.forEach(topic -> {
            try {
                FirebaseMessaging.getInstance(FirebaseApp.getInstance("my-app"))
                        .subscribeToTopic(List.of(deviceToken), topic);
            } catch (FirebaseMessagingException e) {
                throw new ResponseException(BadRequestError.FIREBASE_SUBSCRIBE_TOPIC_FAILED);
            }
        });
    }

    public void unsubscribeDeviceFromTopic(UnRegisterDeviceRequest unRegisterDeviceRequest, List<String> topics) {
        topics.forEach(topic -> {
            try {
                FirebaseMessaging.getInstance(FirebaseApp.getInstance("my-app"))
                        .unsubscribeFromTopic(List.of(unRegisterDeviceRequest.getDeviceToken()), topic);
            } catch (FirebaseMessagingException e) {
                throw new ResponseException(BadRequestError.FIREBASE_SUBSCRIBE_TOPIC_FAILED);
            }
        });
    }
}
