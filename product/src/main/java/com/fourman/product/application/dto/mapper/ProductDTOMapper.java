package com.fourman.product.application.dto.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fourman.common.dto.response.DTOMapper;
import com.fourman.common.dto.response.ProductDTO;
import com.fourman.product.domain.Product;
import com.fourman.product.domain.ProductImage;
import com.fourman.product.infrastructure.persistence.entity.ProductEntity;

@Mapper(
        componentModel = "spring",
        uses = {ProductImageDTOMapper.class, ProductVariantDTOMapper.class})
public interface ProductDTOMapper extends DTOMapper<ProductDTO, Product, ProductEntity> {
    @Override
    @Mapping(target = "avatarId", expression = "java(getAvatarId(product))")
    ProductDTO domainModelToDTO(Product product);

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
}
