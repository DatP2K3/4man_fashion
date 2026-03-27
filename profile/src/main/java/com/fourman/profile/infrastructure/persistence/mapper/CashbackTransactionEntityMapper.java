package com.fourman.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.profile.domain.CashbackTransaction;
import com.fourman.profile.infrastructure.persistence.entity.CashbackTransactionEntity;

@Mapper(componentModel = "Spring")
public interface CashbackTransactionEntityMapper extends EntityMapper<CashbackTransaction, CashbackTransactionEntity> {}
