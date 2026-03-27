package com.fourman.notification.presentation.rest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.common.dto.response.Response;
import com.fourman.notification.application.dto.request.RegisterOrUpdateDeviceRequest;
import com.fourman.notification.application.dto.request.UnRegisterDeviceRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Device Registration API")
@RequestMapping("/api/device-registration")
@Validated
public interface DeviceRegistrationController {

    @Operation(summary = "Register device for FCM")
    @PostMapping("/register")
    Response<Void> registerDevice(@RequestBody RegisterOrUpdateDeviceRequest request);

    @Operation(summary = "Unregister device from FCM")
    @DeleteMapping("/unregister")
    Response<Void> unRegisterDevice(@RequestBody UnRegisterDeviceRequest unRegisterDeviceRequest);
}
