package com.fourman.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.profile.domain.Profile;
import com.fourman.profile.infrastructure.persistence.entity.ProfileEntity;

@Mapper(componentModel = "Spring")
public interface ProfileEntityMapper extends EntityMapper<Profile, ProfileEntity> {}
