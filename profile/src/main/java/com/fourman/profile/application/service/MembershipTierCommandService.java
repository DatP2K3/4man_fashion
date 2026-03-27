package com.fourman.profile.application.service;

import java.util.UUID;

import com.fourman.common.dto.response.MembershipTierDTO;
import com.fourman.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;

public interface MembershipTierCommandService {
    MembershipTierDTO create(CreateOrUpdateMembershipTierRequest createMemberShipTierRequest);

    MembershipTierDTO update(CreateOrUpdateMembershipTierRequest updateMemberShipTierRequest);

    UUID getDefaultMembershipTierId();

    void delete(UUID id, boolean deleted);

    UUID handleMembershipTierChange(Long amount);

    void toggleVisibility(String id);
}
