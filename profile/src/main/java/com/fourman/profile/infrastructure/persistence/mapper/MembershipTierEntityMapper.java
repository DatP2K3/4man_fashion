package com.fourman.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.profile.domain.MembershipTier;
import com.fourman.profile.infrastructure.persistence.entity.MembershipTierEntity;

@Mapper(componentModel = "Spring")
public interface MembershipTierEntityMapper extends EntityMapper<MembershipTier, MembershipTierEntity> {}
