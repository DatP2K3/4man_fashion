package com.fourman.cart.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourman.common.entity.AuditEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cart_items")
public class CartItemEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_variant_id")
    private UUID productVariantId;

    @Column(name = "cart_id")
    private UUID cartId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "deleted")
    private Boolean deleted;
}
