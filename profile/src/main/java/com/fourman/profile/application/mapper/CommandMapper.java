package com.fourman.profile.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.event.ProcessCashbackEvent;
import com.fourman.common.dto.event.UseCashbackEvent;
import com.fourman.profile.application.dto.request.*;
import com.fourman.profile.domain.command.*;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateMembershipTierCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateMembershipTierRequest request);

    CreateOrUpdateShippingAddressCmd fromCreateOrUpdateProfileRequest(CreateOrUpdateAddressRequest request);

    UpdateProfileInfoCmd from(UpdateProfileInfoRequest request);

    ProcessCashbackCmd from(ProcessCashbackEvent event);

    UseCashbackCmd from(UseCashbackEvent event);
}
