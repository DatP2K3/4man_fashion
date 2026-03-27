package com.fourman.storage.application.mapper;

import org.mapstruct.Mapper;

import com.fourman.storage.application.dto.request.UpdateFileRequest;
import com.fourman.storage.domain.command.UpdateFileCmd;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    UpdateFileCmd from(UpdateFileRequest request);
}
