package com.evo.profile.application.service;

import java.util.UUID;

import com.evo.common.dto.response.MembershipTierDTO;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;

public interface MembershipTierService {
    MembershipTierDTO create(CreateOrUpdateMembershipTierRequest createMemberShipTierRequest);

    MembershipTierDTO update(CreateOrUpdateMembershipTierRequest updateMemberShipTierRequest);

    UUID getDefaultMembershipTierId();

    void delete(UUID id, boolean deleted);
}
