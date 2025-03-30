package com.evo.product.application.mapper;

import com.evo.product.application.dto.request.CreateOrUpdateCategoryRequest;
import com.evo.product.application.dto.request.CreateTagDescriptionRequest;
import com.evo.product.domain.command.CreateOrUpdateCategoryCmd;
import com.evo.product.domain.command.CreateTagDescriptionCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateOrUpdateCategoryCmd from(CreateOrUpdateCategoryRequest request);
    CreateTagDescriptionCmd from(CreateTagDescriptionRequest request);
}
