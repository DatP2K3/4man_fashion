package com.evo.product.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_variants")
public class ProductVariantEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sku")
    private String sku;
}
