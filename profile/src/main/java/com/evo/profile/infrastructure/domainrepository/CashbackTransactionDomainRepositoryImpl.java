package com.evo.profile.infrastructure.domainrepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.evo.common.enums.CashbackTransactionType;
import com.evo.common.repository.AbstractDomainRepository;
import com.evo.profile.domain.CashbackTransaction;
import com.evo.profile.domain.repository.CashbackTransactionDomainRepository;
import com.evo.profile.infrastructure.persistence.entity.CashbackTransactionEntity;
import com.evo.profile.infrastructure.persistence.mapper.CashbackTransactionEntityMapper;
import com.evo.profile.infrastructure.persistence.repository.CashbackTransactionEntityRepository;
import com.evo.profile.infrastructure.support.exception.AppErrorCode;
import com.evo.profile.infrastructure.support.exception.AppException;

@Repository
public class CashbackTransactionDomainRepositoryImpl
        extends AbstractDomainRepository<CashbackTransaction, CashbackTransactionEntity, UUID>
        implements CashbackTransactionDomainRepository {
    private final CashbackTransactionEntityRepository cashbackTransactionEntityRepository;
    private final CashbackTransactionEntityMapper cashbackTransactionEntityMapper;

    public CashbackTransactionDomainRepositoryImpl(
            CashbackTransactionEntityRepository cashbackTransactionEntityRepository,
            CashbackTransactionEntityMapper cashbackTransactionEntityMapper) {
        super(cashbackTransactionEntityRepository, cashbackTransactionEntityMapper);
        this.cashbackTransactionEntityRepository = cashbackTransactionEntityRepository;
        this.cashbackTransactionEntityMapper = cashbackTransactionEntityMapper;
    }

    @Override
    public List<CashbackTransaction> findByUserId(UUID userId) {
        List<CashbackTransactionEntity> entities = cashbackTransactionEntityRepository.findByUserId(userId);
        return cashbackTransactionEntityMapper.toDomainModelList(entities);
    }

    @Override
    public List<CashbackTransaction> findByUserIdAndType(UUID userId, CashbackTransactionType type) {
        List<CashbackTransactionEntity> entities =
                cashbackTransactionEntityRepository.findByUserIdAndType(userId, type);
        return cashbackTransactionEntityMapper.toDomainModelList(entities);
    }

    @Override
    public CashbackTransaction getById(UUID uuid) {
        CashbackTransactionEntity entity = cashbackTransactionEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.CASHBACK_TRANSACTION_NOT_FOUND));
        return cashbackTransactionEntityMapper.toDomainModel(entity);
    }
}
