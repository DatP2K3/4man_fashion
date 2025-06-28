package com.evo.product.infrastructure.domainrepository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.evo.common.enums.DiscountStatus;
import com.evo.common.repository.AbstractDomainRepository;
import com.evo.product.domain.Discount;
import com.evo.product.domain.repository.DiscountDomainRepository;
import com.evo.product.infrastructure.persistence.entity.DiscountEntity;
import com.evo.product.infrastructure.persistence.mapper.DiscountEntityMapper;
import com.evo.product.infrastructure.persistence.repository.DiscountEntityRepository;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;

@Repository
public class DiscountDomainRepositoryImpl extends AbstractDomainRepository<Discount, DiscountEntity, UUID>
        implements DiscountDomainRepository {
    private final DiscountEntityMapper discountEntityMapper;
    private final DiscountEntityRepository discountEntityRepository;

    public DiscountDomainRepositoryImpl(
            DiscountEntityMapper discountEntityMapper, DiscountEntityRepository discountEntityRepository) {
        super(discountEntityRepository, discountEntityMapper);
        this.discountEntityRepository = discountEntityRepository;
        this.discountEntityMapper = discountEntityMapper;
    }

    @Override
    public Discount getById(UUID uuid) {
        DiscountEntity discountEntity = discountEntityRepository
                .findById(uuid)
                .orElseThrow(() -> new AppException(AppErrorCode.DISCOUNT_NOT_FOUND));
        return discountEntityMapper.toDomainModel(discountEntity);
    }

    @Override
    public List<Discount> getAll() {
        List<DiscountEntity> discountEntities = discountEntityRepository.findAll();
        return discountEntityMapper.toDomainModelList(discountEntities);
    }

    @Override
    public List<Discount> getAllNotIn(List<DiscountStatus> discountStatuses) {
        List<DiscountEntity> discountEntities = discountEntityRepository.findAllByStatusNotIn(discountStatuses);
        return discountEntityMapper.toDomainModelList(discountEntities);
    }
}
