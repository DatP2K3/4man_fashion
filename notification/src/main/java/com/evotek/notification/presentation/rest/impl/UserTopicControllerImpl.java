package com.evotek.notification.presentation.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.request.UpdateTopicsOfUserRequest;
import com.evo.common.dto.response.ApiResponses;
import com.evotek.notification.application.service.push.impl.command.UserTopicCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserTopicControllerImpl implements UserTopicController {
    private final UserTopicCommandService userTopicCommandService;

    @Override
    public ApiResponses<Void> initUserTopic(@PathVariable UUID userId) {
        this.userTopicCommandService.initUserTopic(userId);
        return ApiResponses.ok();
    }

    @Override
    public ApiResponses<Void> updateTopicOfUser(@RequestBody UpdateTopicsOfUserRequest updateTopicsOfUserRequest) {
        this.userTopicCommandService.updateTopicOfUser(updateTopicsOfUserRequest);
        return ApiResponses.ok();
    }
}
