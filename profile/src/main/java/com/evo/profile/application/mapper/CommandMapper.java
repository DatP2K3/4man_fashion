package com.evo.profile.application.mapper;

import org.mapstruct.Mapper;

import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.profile.application.dto.request.CreateOrUpdateProfileRequest;
import com.evo.profile.domain.command.*;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateMembershipTierCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateMembershipTierRequest request);

    CreateOrUpdateShippingAddressCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateProfileRequest request);

    CreateOrUpdateProfileCmd from(CreateOrUpdateProfileRequest request);
}
