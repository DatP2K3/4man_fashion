package com.fourman.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.product.domain.Discount;
import com.fourman.product.infrastructure.persistence.entity.DiscountEntity;

@Mapper(componentModel = "Spring")
public interface DiscountEntityMapper extends EntityMapper<Discount, DiscountEntity> {}
