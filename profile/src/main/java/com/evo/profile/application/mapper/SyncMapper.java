package com.evo.profile.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.request.SyncUserRequest;
import com.evo.profile.application.dto.request.CreateOrUpdateProfileRequest;
import com.evo.profile.domain.Profile;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    SyncUserRequest from(Profile profile);

    CreateOrUpdateProfileRequest from(SyncUserRequest request);
}
