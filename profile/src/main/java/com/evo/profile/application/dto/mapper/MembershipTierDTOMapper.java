package com.evo.profile.application.dto.mapper;

import com.evo.profile.infrastructure.persistence.entity.MembershipTierEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.profile.application.dto.response.MembershipTierDTO;
import com.evo.profile.domain.MembershipTier;

@Mapper(componentModel = "spring")
public interface MembershipTierDTOMapper extends DTOMapper<MembershipTierDTO, MembershipTier, MembershipTierEntity> {}
