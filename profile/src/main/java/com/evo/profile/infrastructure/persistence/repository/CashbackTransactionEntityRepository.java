package com.evo.profile.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evo.common.enums.CashbackTransactionType;
import com.evo.profile.infrastructure.persistence.entity.CashbackTransactionEntity;

public interface CashbackTransactionEntityRepository extends JpaRepository<CashbackTransactionEntity, UUID> {
    List<CashbackTransactionEntity> findByUserId(UUID userId);

    List<CashbackTransactionEntity> findByUserIdAndType(UUID userId, CashbackTransactionType type);
}
