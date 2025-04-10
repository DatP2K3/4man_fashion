package com.evotek.iam.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.event.PushNotificationEvent;
import com.evotek.iam.application.dto.request.ChangePasswordRequest;
import com.evotek.iam.application.dto.request.CreateOrUpdateUserRequest;
import com.evotek.iam.application.dto.response.TokenDTO;
import com.evotek.iam.application.dto.response.UserDTO;

@Service
public interface UserCommandService {
    UserDTO updateMyUser(CreateOrUpdateUserRequest updateUserRequest);

    void lockUser(String username, boolean enabled);

    void testFcm(PushNotificationEvent pushNotificationEvent);
}
