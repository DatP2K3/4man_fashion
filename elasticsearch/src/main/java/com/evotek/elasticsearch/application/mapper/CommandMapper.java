package com.evotek.elasticsearch.application.mapper;

import com.evo.common.dto.request.SyncProductRequest;
import org.mapstruct.Mapper;

import com.evotek.elasticsearch.domain.command.SyncProductCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncProductCmd from(SyncProductRequest syncProductRequest);
}
