package com.evotek.elasticsearch.application.mapper;

import com.evo.common.dto.event.ProductSync;
import org.mapstruct.Mapper;

import com.evo.common.dto.event.ProductEvent;
import com.evotek.elasticsearch.domain.command.SyncProductCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncProductCmd from(ProductSync productSync);
}
