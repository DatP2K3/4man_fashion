package com.evotek.notification.presentation.rest;

import org.springframework.web.bind.annotation.*;

import com.evo.common.dto.response.ApiResponses;
import com.evotek.notification.application.dto.request.RegisterOrUpdateDeviceRequest;
import com.evotek.notification.application.dto.request.UnRegisterDeviceRequest;
import com.evotek.notification.application.service.push.impl.command.DeviceRegistrationCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/device-registration")
@RequiredArgsConstructor
public class DeviceRegistrationControllerImpl implements DeviceRegistrationController {
    private final DeviceRegistrationCommandService deviceRegistrationCommandService;

    @Override
    public ApiResponses<Void> registerDevice(@RequestBody RegisterOrUpdateDeviceRequest request) {
        this.deviceRegistrationCommandService.registerDevice(request);
        return ApiResponses.ok();
    }

    @Override
    public ApiResponses<Void> unRegisterDevice(@RequestBody UnRegisterDeviceRequest unRegisterDeviceRequest) {
        this.deviceRegistrationCommandService.unregisterDevice(unRegisterDeviceRequest);
        return ApiResponses.ok();
    }
}
