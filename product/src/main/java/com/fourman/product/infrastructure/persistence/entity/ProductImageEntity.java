package com.fourman.product.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourman.common.entity.AuditEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;import lombok.Builder;
import lombok.EqualsAndHashCode;import lombok.Data;
import lombok.EqualsAndHashCode;import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_images")
public class ProductImageEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "file_id")
    private UUID fileId;

    @Column(name = "avatar")
    private Boolean avatar;

    @Column(name = "deleted")
    private Boolean deleted;
}
