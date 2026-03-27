package com.fourman.profile.infrastructure.domainrepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fourman.common.enums.CashbackTransactionType;
import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.profile.domain.CashbackTransaction;
import com.fourman.profile.domain.repository.CashbackTransactionDomainRepository;
import com.fourman.profile.infrastructure.persistence.entity.CashbackTransactionEntity;
import com.fourman.profile.infrastructure.persistence.mapper.CashbackTransactionEntityMapper;
import com.fourman.profile.infrastructure.persistence.repository.CashbackTransactionEntityRepository;
import com.fourman.profile.infrastructure.support.exception.NotFoundError;

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
                .orElseThrow(() -> new ResponseException(NotFoundError.CASHBACK_TRANSACTION_NOT_FOUND));
        return cashbackTransactionEntityMapper.toDomainModel(entity);
    }
}
