package com.evo.product.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.evo.common.mapper.EntityMapper;
import com.evo.product.domain.Discount;
import com.evo.product.infrastructure.persistence.entity.DiscountEntity;

@Mapper(componentModel = "Spring")
public interface DiscountEntityMapper extends EntityMapper<Discount, DiscountEntity> {}
