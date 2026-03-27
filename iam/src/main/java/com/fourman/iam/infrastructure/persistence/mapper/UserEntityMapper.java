package com.fourman.iam.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.iam.domain.User;
import com.fourman.iam.infrastructure.persistence.entity.UserEntity;

@Mapper(componentModel = "Spring")
public interface UserEntityMapper extends EntityMapper<User, UserEntity> {}
