package com.evo.profile.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.profile.application.dto.response.CashbackTransactionDTO;
import com.evo.profile.domain.CashbackTransaction;
import com.evo.profile.infrastructure.persistence.entity.CashbackTransactionEntity;

@Mapper(componentModel = "spring")
public interface CashbackTransactionDTOMapper
        extends DTOMapper<CashbackTransactionDTO, CashbackTransaction, CashbackTransactionEntity> {}
