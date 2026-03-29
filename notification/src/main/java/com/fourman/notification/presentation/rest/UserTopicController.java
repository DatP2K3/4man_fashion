package com.fourman.notification.presentation.rest;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.common.dto.request.UpdateTopicsOfUserRequest;
import com.fourman.common.dto.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Topic API")
@RequestMapping("/api")
@Validated
public interface UserTopicController {

    @Operation(summary = "Initialize user topic")
    @PostMapping("/user-token/{userId}")
    Response<Void> initUserTopic(@PathVariable UUID userId);

    @Operation(summary = "Update topic of user")
    @PutMapping("/user-token/update")
    Response<Void> updateTopicOfUser(@Valid @RequestBody UpdateTopicsOfUserRequest updateTopicsOfUserRequest);
}
