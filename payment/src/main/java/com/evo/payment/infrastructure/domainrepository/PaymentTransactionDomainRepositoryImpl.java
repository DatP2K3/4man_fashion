package com.evo.payment.infrastructure.domainrepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.evo.common.repository.AbstractDomainRepository;
import com.evo.payment.domain.PaymentTransaction;
import com.evo.payment.domain.repository.PaymentTransactionDomainRepository;
import com.evo.payment.infrastructure.persistence.entity.PaymentTransactionEntity;
import com.evo.payment.infrastructure.persistence.mapper.PaymentTransactionEntityMapper;
import com.evo.payment.infrastructure.persistence.repository.PaymentTransactionEntityRepository;
import com.evo.payment.infrastructure.support.exception.AppErrorCode;
import com.evo.payment.infrastructure.support.exception.AppException;

@Repository
public class PaymentTransactionDomainRepositoryImpl
        extends AbstractDomainRepository<PaymentTransaction, PaymentTransactionEntity, UUID>
        implements PaymentTransactionDomainRepository {
    private final PaymentTransactionEntityRepository paymentTransactionEntityRepository;
    private final PaymentTransactionEntityMapper paymentTransactionEntityMapper;

    public PaymentTransactionDomainRepositoryImpl(
            PaymentTransactionEntityMapper paymentTransactionEntityMapper,
            PaymentTransactionEntityRepository paymentTransactionEntityRepository) {
        super(paymentTransactionEntityRepository, paymentTransactionEntityMapper);
        this.paymentTransactionEntityRepository = paymentTransactionEntityRepository;
        this.paymentTransactionEntityMapper = paymentTransactionEntityMapper;
    }

    @Override
    public PaymentTransaction getById(UUID uuid) {
        PaymentTransactionEntity paymentTransactionEntity = paymentTransactionEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.PAYMENT_TRANSACTION_NOT_FOUND));
        return paymentTransactionEntityMapper.toDomainModel(paymentTransactionEntity);
    }
}
