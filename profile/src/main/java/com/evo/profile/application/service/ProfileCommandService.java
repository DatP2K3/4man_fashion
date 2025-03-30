package com.evo.profile.application.service;

import java.util.UUID;

import com.evo.profile.application.dto.request.CreateOrUpdateProfileRequest;
import com.evo.profile.application.dto.response.ProfileDTO;
import com.evo.profile.application.dto.response.ShippingAddressDTO;

public interface ProfileCommandService {
    void syncCreate(CreateOrUpdateProfileRequest createProfileRequest);

    ProfileDTO updateProfile(CreateOrUpdateProfileRequest updateProfileRequest);

    void delete(UUID id, boolean deleted);

    ShippingAddressDTO createShippingAddress(CreateOrUpdateProfileRequest request);

    ShippingAddressDTO updateShippingAddress(CreateOrUpdateProfileRequest request);

    void syncUpdate(CreateOrUpdateProfileRequest request);
}
