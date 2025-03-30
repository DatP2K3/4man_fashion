package com.evo.product.infrastructure.persistence.repository;

import com.evo.product.infrastructure.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, UUID> {
}
