package com.evo.profile.application.mapper;

import org.mapstruct.Mapper;

import com.evo.profile.application.dto.request.CreateOrUpdateAddressRequest;
import com.evo.profile.application.dto.request.CreateOrUpdateMembershipTierRequest;
import com.evo.profile.application.dto.request.UpdateProfileInfoRequest;
import com.evo.profile.domain.command.*;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateMembershipTierCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateMembershipTierRequest request);

    CreateOrUpdateShippingAddressCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateAddressRequest request);

    UpdateProfileInfoCmd from(UpdateProfileInfoRequest request);
}
