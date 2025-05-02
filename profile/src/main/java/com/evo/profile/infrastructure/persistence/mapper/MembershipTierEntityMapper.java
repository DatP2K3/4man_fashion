package com.evo.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.profile.domain.MembershipTier;
import com.evo.profile.infrastructure.persistence.entity.MembershipTierEntity;

@Mapper(componentModel = "Spring")
public interface MembershipTierEntityMapper extends EntityMapper<MembershipTier, MembershipTierEntity> {}
