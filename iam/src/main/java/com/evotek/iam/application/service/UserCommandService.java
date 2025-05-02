package com.evotek.iam.application.service;

import org.springframework.stereotype.Service;

import com.evo.common.dto.event.PushNotificationEvent;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import com.evotek.iam.application.dto.response.UserDTO;

@Service
public interface UserCommandService {
    UserDTO updateMyUser(CreateOrUpdateUserRequest updateUserRequest);

    void lockUser(String username, boolean enabled);

    void testFcm(PushNotificationEvent pushNotificationEvent);
}
