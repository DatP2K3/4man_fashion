package com.fourman.cart.infrastructure.persistence.entity;

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
@Table(name = "carts")
public class CartEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;
}
