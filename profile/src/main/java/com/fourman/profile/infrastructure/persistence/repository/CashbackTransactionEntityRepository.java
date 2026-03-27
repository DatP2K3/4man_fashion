package com.fourman.profile.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourman.common.enums.CashbackTransactionType;
import com.fourman.profile.infrastructure.persistence.entity.CashbackTransactionEntity;

public interface CashbackTransactionEntityRepository extends JpaRepository<CashbackTransactionEntity, UUID> {
    List<CashbackTransactionEntity> findByUserId(UUID userId);

    List<CashbackTransactionEntity> findByUserIdAndType(UUID userId, CashbackTransactionType type);
}
