package com.evo.profile.domain.repository;

import java.util.List;
import java.util.UUID;

import com.evo.common.enums.CashbackTransactionType;
import com.evo.common.repository.DomainRepository;
import com.evo.profile.domain.CashbackTransaction;

public interface CashbackTransactionDomainRepository extends DomainRepository<CashbackTransaction, UUID> {
    List<CashbackTransaction> findByUserId(UUID userId);

    List<CashbackTransaction> findByUserIdAndType(UUID userId, CashbackTransactionType type);
}
