package com.evo.payment.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment_transactions")
public class PaymentTransactionEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "pay_date")
    private Instant payDate;

    @Column(name = "transaction_info")
    private String transactionInfo;
}
