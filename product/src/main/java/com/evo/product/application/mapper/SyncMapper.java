package com.evo.product.application.mapper;

import com.evo.product.domain.ProductImage;
import org.mapstruct.Mapper;

import com.evo.common.dto.request.SyncProductRequest;
import com.evo.product.domain.Product;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    @Mapping(target = "avatarId", expression = "java(getAvatarId(product))")
    SyncProductRequest from(Product product);

    default UUID getAvatarId(Product product) {
        if (product.getProductImages() != null) {
            return product.getProductImages().stream()
                    .filter(ProductImage::getAvatar)
                    .map(ProductImage::getId)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
