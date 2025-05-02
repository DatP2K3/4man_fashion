package com.evo.product.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.DiscountStatus;
import com.evo.common.enums.DiscountType;

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
@Table(name = "discounts")
public class DiscountEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DiscountStatus status;

    @Column(name = "discount_type")
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "percentage")
    private Integer discountPercentage;

    @Column(name = "discount_price")
    private Long discountPrice;
}
