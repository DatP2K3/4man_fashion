package com.fourman.product.infrastructure.persistence.entity;

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
@Table(name = "tag_descriptions")
public class TagDescriptionEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "deleted")
    private boolean deleted;
}
