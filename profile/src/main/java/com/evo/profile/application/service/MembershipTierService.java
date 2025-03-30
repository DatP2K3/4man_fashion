package com.evo.profile.application.service;

import java.util.UUID;

import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.profile.application.dto.response.MembershipTierDTO;

public interface MembershipTierService {
    MembershipTierDTO create(CreateOrUpdateMembershipTierRequest createMemberShipTierRequest);

    MembershipTierDTO update(CreateOrUpdateMembershipTierRequest updateMemberShipTierRequest);

    UUID getDefaultMembershipTierId();

    void delete(UUID id, boolean deleted);
}
