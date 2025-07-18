package com.evo.profile.presentation.rest.impl;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.PagingResponse;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.common.dto.response.Response;
import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;
import com.evo.profile.application.service.ProfileCommandService;
import com.evo.profile.application.service.ProfileQueryService;
import com.evo.profile.presentation.rest.ProfileController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    @Override
    public Response<ProfileDTO> initProfile() {
        return Response.of(this.profileCommandService.getOrInitProfile());
    }

    @Override
    public Response<ProfileDTO> createShippingAddress(@RequestBody CreateOrUpdateAddressRequest request) {
        return Response.of(this.profileCommandService.createShippingAddress(request));
    }

    @Override
    public Response<ProfileDTO> updateShippingAddress(@RequestBody CreateOrUpdateAddressRequest request) {
        return Response.of(this.profileCommandService.updateShippingAddress(request));
    }

    @Override
    public Response<ProfileDTO> updateProfile(@RequestBody UpdateProfileInfoRequest request) {
        return Response.of(this.profileCommandService.updateProfile(request));
    }

    @Override
    public Response<ProfileDTO> changeAvatar(@RequestPart MultipartFile file) {
        return Response.of(this.profileCommandService.changeAvatar(file));
    }

    @Override
    public PagingResponse<ProfileDTO> search(SearchProfileRequest searchProfileRequest) {
        return PagingResponse.of(this.profileQueryService.searchProfiles(searchProfileRequest));
    }
}
