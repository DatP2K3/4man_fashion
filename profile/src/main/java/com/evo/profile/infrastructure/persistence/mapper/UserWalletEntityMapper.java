package com.evo.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.profile.domain.UserWallet;
import com.evo.profile.infrastructure.persistence.entity.UserWalletEntity;

@Mapper(componentModel = "Spring")
public interface UserWalletEntityMapper extends EntityMapper<UserWallet, UserWalletEntity> {}
