package com.evo.profile.presentation.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.PageApiResponse;
import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;
import com.evo.profile.application.service.ProfileCommandService;
import com.evo.profile.application.service.ProfileQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    @Override
    public ApiResponses<ProfileDTO> initProfile() {
        return ApiResponses.of(this.profileCommandService.getOrInitProfile());
    }

    @Override
    public ApiResponses<ProfileDTO> createShippingAddress(@RequestBody CreateOrUpdateAddressRequest request) {
        return ApiResponses.of(this.profileCommandService.createShippingAddress(request));
    }

    @Override
    public ApiResponses<ProfileDTO> updateShippingAddress(@RequestBody CreateOrUpdateAddressRequest request) {
        return ApiResponses.of(this.profileCommandService.updateShippingAddress(request));
    }

    @Override
    public ApiResponses<ProfileDTO> updateProfile(@RequestBody UpdateProfileInfoRequest request) {
        return ApiResponses.of(this.profileCommandService.updateProfile(request));
    }

    @Override
    public ApiResponses<ProfileDTO> changeAvatar(@RequestPart MultipartFile file) {
        return ApiResponses.of(this.profileCommandService.changeAvatar(file));
    }

    @Override
    public PageApiResponse<List<ProfileDTO>> search(SearchProfileRequest searchProfileRequest) {
        Long totalProfiles = this.profileQueryService.totalProfiles(searchProfileRequest);
        List<ProfileDTO> profileDTOS = Collections.emptyList();
        if (totalProfiles != 0) {
            profileDTOS = this.profileQueryService.searchProfiles(searchProfileRequest);
        }
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageSize(searchProfileRequest.getPageSize())
                .pageIndex(searchProfileRequest.getPageIndex())
                .totalElements(totalProfiles)
                .totalPages((int) (Math.ceil((double) totalProfiles / searchProfileRequest.getPageSize())))
                .hasNext(searchProfileRequest.getPageIndex() + searchProfileRequest.getPageSize() < totalProfiles)
                .hasPrevious(searchProfileRequest.getPageIndex() > 1)
                .build();

        return PageApiResponse.<List<ProfileDTO>>builder()
                .data(profileDTOS)
                .pageable(pageableResponse)
                .success(true)
                .code(200)
                .message("Search profiles successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
