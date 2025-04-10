package com.evo.profile.presentation.rest;

import com.evo.common.dto.response.PageApiResponse;
import com.evo.profile.application.dto.request.SearchProfileRequest;
import com.evo.profile.application.service.ProfileQueryService;
import com.evo.profile.domain.query.SearchProfileQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.ApiResponses;
import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;
import com.evo.profile.application.dto.response.ProfileDTO;
import com.evo.profile.application.dto.response.ShippingAddressDTO;
import com.evo.profile.application.service.ProfileCommandService;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    @PostMapping("/profiles")
    ApiResponses<ProfileDTO> initProfile() {
        ProfileDTO profileDTO = profileCommandService.getOrInitProfile();
        return ApiResponses.<ProfileDTO>builder()
                .data(profileDTO)
                .success(true)
                .code(201)
                .message("Profile created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/profiles/shipping-address")
    ApiResponses<ProfileDTO> createShippingAddress(@RequestBody CreateOrUpdateAddressRequest request) {
        ProfileDTO profileDTO = profileCommandService.createShippingAddress(request);
        return ApiResponses.<ProfileDTO>builder()
                .data(profileDTO)
                .success(true)
                .code(201)
                .message("ShippingAddress created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/profiles/shipping-address")
    ApiResponses<ProfileDTO> updateShippingAddress(@RequestBody CreateOrUpdateAddressRequest request) {
        ProfileDTO profileDTO = profileCommandService.updateShippingAddress(request);
        return ApiResponses.<ProfileDTO>builder()
                .data(profileDTO)
                .success(true)
                .code(200)
                .message("ShippingAddress updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/profiles")
    ApiResponses<ProfileDTO> updateProfile(@RequestBody UpdateProfileInfoRequest request) {
        ProfileDTO profileDTO = profileCommandService.updateProfile(request);
        return ApiResponses.<ProfileDTO>builder()
                .data(profileDTO)
                .success(true)
                .code(200)
                .message("Profile updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/profiles/avatar")
    public ApiResponses<ProfileDTO> changeAvatar(@RequestPart MultipartFile file) {
        ProfileDTO profileDTO = profileCommandService.changeAvatar(file);
        return ApiResponses.<ProfileDTO>builder()
                .data(profileDTO)
                .success(true)
                .code(200)
                .message("Avatar successfully changed")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/all-profiles")
    public PageApiResponse<List<ProfileDTO>> search(@RequestBody SearchProfileRequest searchProfileRequest) {
        Long totalProfiles = profileQueryService.totalProfiles(searchProfileRequest);
        List<ProfileDTO> profileDTOS = Collections.emptyList();
        if(totalProfiles != 0) {
            profileDTOS = profileQueryService.searchProfiles(searchProfileRequest);
        }
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageSize(searchProfileRequest.getPageSize())
                .pageIndex(searchProfileRequest.getPageIndex())
                .totalElements(totalProfiles)
                .totalPages((int)(Math.ceil((double)totalProfiles / searchProfileRequest.getPageSize())))
                .hasNext(searchProfileRequest.getPageIndex() + searchProfileRequest.getPageSize() < totalProfiles)
                .hasPrevious(searchProfileRequest.getPageIndex() > 1).build();

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
