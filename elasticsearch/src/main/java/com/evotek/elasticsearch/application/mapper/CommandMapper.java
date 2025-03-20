package com.evotek.elasticsearch.application.mapper;

import org.mapstruct.Mapper;

import com.evo.common.dto.request.SyncUserRequest;
import com.evotek.elasticsearch.domain.command.SyncUserCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncUserCmd from(SyncUserRequest syncUserRequest);
}
