package com.fourman.notification.presentation.rest.impl;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.request.UpdateTopicsOfUserRequest;
import com.fourman.common.dto.response.Response;
import com.fourman.notification.application.service.push.impl.command.UserTopicCommandService;
import com.fourman.notification.presentation.rest.UserTopicController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTopicControllerImpl implements UserTopicController {
    private final UserTopicCommandService userTopicCommandService;

    @Override
    public Response<Void> initUserTopic(@PathVariable UUID userId) {
        this.userTopicCommandService.initUserTopic(userId);
        return Response.ok();
    }

    @Override
    public Response<Void> updateTopicOfUser(@RequestBody UpdateTopicsOfUserRequest updateTopicsOfUserRequest) {
        this.userTopicCommandService.updateTopicOfUser(updateTopicsOfUserRequest);
        return Response.ok();
    }
}
