package com.fourman.payment.infrastructure.domainrepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.payment.domain.PaymentTransaction;
import com.fourman.payment.domain.repository.PaymentTransactionDomainRepository;
import com.fourman.payment.infrastructure.persistence.entity.PaymentTransactionEntity;
import com.fourman.payment.infrastructure.persistence.mapper.PaymentTransactionEntityMapper;
import com.fourman.payment.infrastructure.persistence.repository.PaymentTransactionEntityRepository;
import com.fourman.payment.infrastructure.support.exception.NotFoundError;

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
                .orElseThrow(() -> new ResponseException(NotFoundError.PAYMENT_TRANSACTION_NOT_FOUND));
        return paymentTransactionEntityMapper.toDomainModel(paymentTransactionEntity);
    }
}
