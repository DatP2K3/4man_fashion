package com.fourman.elasticsearch.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.dto.event.ProductSync;
import com.fourman.elasticsearch.domain.command.SyncProductCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncProductCmd from(ProductSync productSync);
}
