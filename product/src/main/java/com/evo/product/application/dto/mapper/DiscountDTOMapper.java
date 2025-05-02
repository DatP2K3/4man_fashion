package com.evo.product.application.dto.mapper;

import com.evo.product.infrastructure.persistence.entity.DiscountEntity;
import org.mapstruct.Mapper;

import com.evo.common.dto.response.DTOMapper;
import com.evo.product.application.dto.response.DiscountDTO;
import com.evo.product.domain.Discount;

@Mapper(componentModel = "spring")
public interface DiscountDTOMapper extends DTOMapper<DiscountDTO, Discount, DiscountEntity> {}
