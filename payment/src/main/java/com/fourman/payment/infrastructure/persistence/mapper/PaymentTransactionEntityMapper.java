package com.fourman.payment.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.payment.domain.PaymentTransaction;
import com.fourman.payment.infrastructure.persistence.entity.PaymentTransactionEntity;

@Mapper(componentModel = "Spring")
public interface PaymentTransactionEntityMapper extends EntityMapper<PaymentTransaction, PaymentTransactionEntity> {}
