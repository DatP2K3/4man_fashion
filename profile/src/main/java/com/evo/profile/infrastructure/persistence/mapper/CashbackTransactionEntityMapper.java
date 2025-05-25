package com.evo.profile.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.profile.domain.CashbackTransaction;
import com.evo.profile.infrastructure.persistence.entity.CashbackTransactionEntity;

@Mapper(componentModel = "Spring")
public interface CashbackTransactionEntityMapper extends EntityMapper<CashbackTransaction, CashbackTransactionEntity> {}
