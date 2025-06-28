package com.evotek.storage.infrastructure.persistence.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.evotek.storage.infrastructure.persistence.entity.FileEntity;
import com.evotek.storage.infrastructure.persistence.repository.custom.FileEntityRepositoryCustom;

public interface FileEntityRepository extends JpaRepository<FileEntity, UUID>, FileEntityRepositoryCustom {
    @Query("SELECT f FROM FileEntity f WHERE f.createdAt < :cutoffTime AND f.usageStatus = 'TEMPORARY'")
    List<FileEntity> findTemporaryFilesCreatedBefore(Instant cutoffTime);
}
