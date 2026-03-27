package com.fourman.profile.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.MembershipTierDTO;
import com.fourman.profile.domain.MembershipTier;
import com.fourman.profile.infrastructure.persistence.entity.MembershipTierEntity;

@Mapper(componentModel = "spring")
public interface MembershipTierDTOMapper extends DTOMapper<MembershipTierDTO, MembershipTier, MembershipTierEntity> {}
