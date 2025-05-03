package com.evo.product.application.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evo.common.dto.event.ProductSync;
import com.evo.product.domain.Product;
import com.evo.product.domain.ProductImage;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    @Mapping(target = "avatarId", expression = "java(getAvatarId(product))")
    ProductSync from(Product product);

    default UUID getAvatarId(Product product) {
        if (product.getProductImages() != null) {
            return product.getProductImages().stream()
                    .filter(ProductImage::getAvatar)
                    .map(ProductImage::getFileId)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    List<ProductSync> from(List<Product> products);
}
