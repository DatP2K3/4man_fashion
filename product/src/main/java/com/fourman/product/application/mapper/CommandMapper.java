package com.fourman.product.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.product.application.dto.request.*;
import com.fourman.product.domain.command.*;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateCategoryCmd from(CreateOrUpdateCategoryRequest request);

    CreateTagDescriptionCmd from(CreateTagDescriptionRequest request);

    CreateOrUpdateProductCmd from(CreateOrUpdateProductRequest request);

    CreateOrUpdateProductVariantCmd from(CreateOrUpdateProductVariantRequest request);

    CreateOrUpdateProductImageCmd from(CreateOrUpdateProductImageRequest request);

    CreateOrUpdateDiscountCmd from(CreateOrUpdateDiscountRequest request);
}
