package com.evo.profile.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.event.ProcessCashbackEvent;
import com.evo.common.dto.event.UseCashbackEvent;
import com.evo.profile.application.dto.request.*;
import com.evo.profile.domain.command.*;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateMembershipTierCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateMembershipTierRequest request);

    CreateOrUpdateShippingAddressCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateAddressRequest request);

    UpdateProfileInfoCmd from(UpdateProfileInfoRequest request);

    ProcessCashbackCmd from(ProcessCashbackEvent event);

    UseCashbackCmd from(UseCashbackEvent event);
}
