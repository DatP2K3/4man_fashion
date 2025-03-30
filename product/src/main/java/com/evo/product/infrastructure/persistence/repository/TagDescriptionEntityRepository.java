package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.TagDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TagDescriptionEntityRepository extends JpaRepository<TagDescriptionEntity, UUID> {
    List<TagDescriptionEntity> findByCategoryIdInAndDeletedFalse(List<UUID> categoryIds);
}
