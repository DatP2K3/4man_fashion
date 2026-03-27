package com.fourman.payment.domain.repository;

import java.util.UUID;

import com.fourman.common.repository.DomainRepository;
import com.fourman.payment.domain.PaymentTransaction;

public interface PaymentTransactionDomainRepository extends DomainRepository<PaymentTransaction, UUID> {}
