package com.evo.product.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.product.infrastructure.persistence.entity.CommentEntity;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, UUID> {}
