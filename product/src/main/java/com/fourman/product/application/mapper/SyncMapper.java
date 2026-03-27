package com.fourman.product.application.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fourman.common.dto.event.ProductSync;
import com.fourman.common.dto.event.ProductVariantSync;
import com.fourman.product.domain.Product;
import com.fourman.product.domain.ProductImage;
import com.fourman.product.domain.command.UpdateProductVariantQuantityCmd;

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

    List<UpdateProductVariantQuantityCmd> fromProductVariantSyncs(List<ProductVariantSync> productVariantSyncs);
}
