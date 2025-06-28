package com.evo.payment.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.payment.infrastructure.persistence.entity.PaymentTransactionEntity;

public interface PaymentTransactionEntityRepository extends JpaRepository<PaymentTransactionEntity, UUID> {}
