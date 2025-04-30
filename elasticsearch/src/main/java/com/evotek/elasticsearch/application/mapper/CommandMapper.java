package com.evotek.elasticsearch.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.event.ProductEvent;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncProductCmd from(ProductEvent productEvent);
}
