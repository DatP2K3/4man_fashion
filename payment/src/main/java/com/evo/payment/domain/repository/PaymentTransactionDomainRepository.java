package com.evo.payment.domain.repository;

import java.util.UUID;

import com.evo.common.repository.DomainRepository;
import com.evo.payment.domain.PaymentTransaction;

public interface PaymentTransactionDomainRepository extends DomainRepository<PaymentTransaction, UUID> {}
