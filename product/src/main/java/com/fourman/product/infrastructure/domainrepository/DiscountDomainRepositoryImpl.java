package com.fourman.product.infrastructure.domainrepository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.fourman.common.enums.DiscountStatus;
import com.fourman.common.exception.ResponseException;
import com.fourman.common.repository.AbstractDomainRepository;
import com.fourman.product.domain.Discount;
import com.fourman.product.domain.repository.DiscountDomainRepository;
import com.fourman.product.infrastructure.persistence.entity.DiscountEntity;
import com.fourman.product.infrastructure.persistence.mapper.DiscountEntityMapper;
import com.fourman.product.infrastructure.persistence.repository.DiscountEntityRepository;
import com.fourman.product.infrastructure.support.exception.NotFoundError;

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
                .orElseThrow(() -> new ResponseException(NotFoundError.DISCOUNT_NOT_FOUND));
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
