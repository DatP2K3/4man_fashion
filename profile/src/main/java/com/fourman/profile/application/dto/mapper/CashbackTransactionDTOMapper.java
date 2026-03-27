package com.fourman.profile.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.profile.application.dto.response.CashbackTransactionDTO;
import com.fourman.profile.domain.CashbackTransaction;
import com.fourman.profile.infrastructure.persistence.entity.CashbackTransactionEntity;

@Mapper(componentModel = "spring")
public interface CashbackTransactionDTOMapper
        extends DTOMapper<CashbackTransactionDTO, CashbackTransaction, CashbackTransactionEntity> {}
