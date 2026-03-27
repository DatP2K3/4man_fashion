package com.fourman.iam.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.iam.domain.OauthClient;
import com.fourman.iam.infrastructure.persistence.entity.OauthClientEntity;

@Mapper(componentModel = "Spring")
public interface OauthClientEntityMapper extends EntityMapper<OauthClient, OauthClientEntity> {}
