package com.evo.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.profile.domain.Profile;
import com.evo.profile.infrastructure.persistence.entity.ProfileEntity;

@Mapper(componentModel = "Spring")
public interface ProfileEntityMapper extends EntityMapper<Profile, ProfileEntity> {}
