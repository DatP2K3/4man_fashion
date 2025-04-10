package com.evo.product.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.request.SyncProductRequest;
import com.evo.product.domain.Product;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    SyncProductRequest from(Product product);
}
