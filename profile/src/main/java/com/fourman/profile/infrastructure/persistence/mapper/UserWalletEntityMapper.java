package com.fourman.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.profile.domain.UserWallet;
import com.fourman.profile.infrastructure.persistence.entity.UserWalletEntity;

@Mapper(componentModel = "Spring")
public interface UserWalletEntityMapper extends EntityMapper<UserWallet, UserWalletEntity> {}
