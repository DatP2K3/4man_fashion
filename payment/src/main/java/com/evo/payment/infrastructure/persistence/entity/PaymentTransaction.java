package com.evo.payment.infrastructure.persistence.entity;

import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment_transactions")
public class PaymentTransaction extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "pay_date")
    private Instant payDate;

    @Column(name = "transaction_info")
    private String transactionInfo;
}
