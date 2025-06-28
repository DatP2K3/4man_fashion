package com.evo.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.payment.domain.PaymentTransaction;
import com.evo.payment.infrastructure.persistence.entity.PaymentTransactionEntity;

@Mapper(componentModel = "Spring")
public interface PaymentTransactionEntityMapper extends EntityMapper<PaymentTransaction, PaymentTransactionEntity> {}
