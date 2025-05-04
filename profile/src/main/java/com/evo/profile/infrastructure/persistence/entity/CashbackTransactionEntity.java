package com.evo.profile.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.enums.CashbackTransactionType;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Table(name = "cashback_transactions")
public class CashbackTransactionEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CashbackTransactionType type;

    @Column(name = "description")
    private String description;
}
