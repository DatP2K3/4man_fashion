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
@Table(name = "comments")
public class CommentEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating")
    private int rating;
}
