package com.evotek.notification.presentation.rest;

import com.evo.common.dto.response.Response;
import com.evotek.notification.application.dto.request.RegisterOrUpdateDeviceRequest;
import com.evotek.notification.application.dto.request.UnRegisterDeviceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
