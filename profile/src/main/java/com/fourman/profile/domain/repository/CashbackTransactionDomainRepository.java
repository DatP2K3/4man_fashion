package com.fourman.profile.domain.repository;

import java.util.List;
import java.util.UUID;

import com.fourman.common.enums.CashbackTransactionType;
import com.fourman.common.repository.DomainRepository;
import com.fourman.profile.domain.CashbackTransaction;

public interface CashbackTransactionDomainRepository extends DomainRepository<CashbackTransaction, UUID> {
    List<CashbackTransaction> findByUserId(UUID userId);

    List<CashbackTransaction> findByUserIdAndType(UUID userId, CashbackTransactionType type);
}
