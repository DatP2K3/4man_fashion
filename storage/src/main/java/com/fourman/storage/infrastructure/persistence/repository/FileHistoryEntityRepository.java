package com.fourman.storage.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourman.storage.infrastructure.persistence.entity.FileHistoryEntity;

public interface FileHistoryEntityRepository extends JpaRepository<FileHistoryEntity, UUID> {}
