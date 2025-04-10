package com.evo.product.application.mapper;

import com.evo.product.application.dto.request.*;
import com.evo.product.domain.command.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateCategoryCmd from(CreateOrUpdateCategoryRequest request);
    CreateTagDescriptionCmd from(CreateTagDescriptionRequest request);
    CreateOrUpdateProductCmd from(CreateOrUpdateProductRequest request);
    CreateOrUpdateProductVariantCmd from(CreateOrUpdateProductVariantRequest request);
    CreateOrUpdateProductImageCmd from(CreateOrUpdateProductImageRequest request);
}
