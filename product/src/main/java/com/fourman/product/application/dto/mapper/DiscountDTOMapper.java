package com.fourman.product.application.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.product.application.dto.response.DiscountDTO;
import com.fourman.product.domain.Discount;
import com.fourman.product.infrastructure.persistence.entity.DiscountEntity;

@Mapper(componentModel = "spring")
public interface DiscountDTOMapper extends DTOMapper<DiscountDTO, Discount, DiscountEntity> {}
