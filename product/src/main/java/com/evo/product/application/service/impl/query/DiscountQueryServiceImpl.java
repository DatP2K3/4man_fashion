package com.evo.product.application.service.impl.query;

import java.util.List;
import java.util.UUID;

import com.evo.product.infrastructure.persistence.entity.DiscountEntity;
import com.evo.product.infrastructure.persistence.mapper.DiscountEntityMapper;
import com.evo.product.infrastructure.persistence.repository.DiscountEntityRepository;
import com.evo.product.infrastructure.support.exception.AppErrorCode;
import com.evo.product.infrastructure.support.exception.AppException;
import org.springframework.stereotype.Service;

import com.evo.product.application.dto.mapper.DiscountDTOMapper;
import com.evo.product.application.dto.response.DiscountDTO;
import com.evo.product.application.mapper.QueryMapper;
import com.evo.product.application.service.DiscountQueryService;
import com.evo.product.domain.Discount;
import com.evo.product.domain.repository.DiscountDomainRepository;

import lombok.RequiredArgsConstructor;

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
        DiscountEntity discountEntity = discountEntityRepository.findById(id).orElseThrow(() -> new AppException(AppErrorCode.DISCOUNT_NOT_FOUND));
        DiscountDTO discountDTO = discountDTOMapper.entityToDTO(discountEntity);
        return discountDTO;
    }
}
