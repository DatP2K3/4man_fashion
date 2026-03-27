package com.fourman.notification.presentation.rest.impl;

import org.springframework.web.bind.annotation.*;

import com.fourman.common.dto.response.Response;
import com.fourman.notification.application.dto.request.RegisterOrUpdateDeviceRequest;
import com.fourman.notification.application.dto.request.UnRegisterDeviceRequest;
import com.fourman.notification.application.service.push.impl.command.DeviceRegistrationCommandService;
import com.fourman.notification.presentation.rest.DeviceRegistrationController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/device-registration")
@RequiredArgsConstructor
public class DeviceRegistrationControllerImpl implements DeviceRegistrationController {
    private final DeviceRegistrationCommandService deviceRegistrationCommandService;

    @Override
    public Response<Void> registerDevice(@RequestBody RegisterOrUpdateDeviceRequest request) {
        this.deviceRegistrationCommandService.registerDevice(request);
        return Response.ok();
    }

    @Override
    public Response<Void> unRegisterDevice(@RequestBody UnRegisterDeviceRequest unRegisterDeviceRequest) {
        this.deviceRegistrationCommandService.unregisterDevice(unRegisterDeviceRequest);
        return Response.ok();
    }
}
