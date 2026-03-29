package com.fourman.product.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourman.common.exception.ResponseException;
import com.fourman.product.application.dto.mapper.DiscountDTOMapper;
import com.fourman.product.application.dto.response.DiscountDTO;
import com.fourman.product.application.mapper.QueryMapper;
import com.fourman.product.application.service.DiscountQueryService;
import com.fourman.product.domain.Discount;
import com.fourman.product.domain.exception.NotFoundError;
import com.fourman.product.domain.repository.DiscountDomainRepository;
import com.fourman.product.infrastructure.persistence.entity.DiscountEntity;
import com.fourman.product.infrastructure.persistence.mapper.DiscountEntityMapper;
import com.fourman.product.infrastructure.persistence.repository.DiscountEntityRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DiscountQueryServiceImpl implements DiscountQueryService {
    private final DiscountDomainRepository discountDomainRepository;
    private final QueryMapper queryMapper;
    private final DiscountDTOMapper discountDTOMapper;
    private final DiscountEntityRepository discountEntityRepository;
    private final DiscountEntityMapper discountEntityMapper;

    @Override
    public List<DiscountDTO> getAll() {
        List<Discount> discounts = discountDomainRepository.getAll();
        return discountDTOMapper.domainModelsToDTOs(discounts);
    }

    @Override
    public DiscountDTO getById(UUID id) {
        DiscountEntity discountEntity = discountEntityRepository
                .findById(id)
                .orElseThrow(() -> new ResponseException(NotFoundError.DISCOUNT_NOT_FOUND));
        DiscountDTO discountDTO = discountDTOMapper.entityToDTO(discountEntity);
        return discountDTO;
    }
}
