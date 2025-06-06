package com.evo.profile.application.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.evo.common.dto.response.ProfileDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;

public interface ProfileCommandService {
    ProfileDTO getOrInitProfile();

    ProfileDTO updateProfile(UpdateProfileInfoRequest updateProfileInfoRequest);

    ProfileDTO changeAvatar(MultipartFile file);

    void delete(UUID id, boolean deleted);

    ProfileDTO createShippingAddress(CreateOrUpdateAddressRequest request);

    ProfileDTO updateShippingAddress(CreateOrUpdateAddressRequest request);
}
