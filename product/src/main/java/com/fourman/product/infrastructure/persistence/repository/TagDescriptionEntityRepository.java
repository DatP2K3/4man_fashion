package com.fourman.product.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourman.product.infrastructure.persistence.entity.TagDescriptionEntity;

public interface TagDescriptionEntityRepository extends JpaRepository<TagDescriptionEntity, UUID> {
    List<TagDescriptionEntity> findByCategoryIdIn(List<UUID> categoryIds);
}
