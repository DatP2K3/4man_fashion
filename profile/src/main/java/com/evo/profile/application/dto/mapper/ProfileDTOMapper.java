package com.evo.profile.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.profile.application.dto.response.ProfileDTO;
import com.evo.profile.domain.Profile;

@Mapper(componentModel = "spring")
public interface ProfileDTOMapper extends DTOMapper<ProfileDTO, Profile> {}
