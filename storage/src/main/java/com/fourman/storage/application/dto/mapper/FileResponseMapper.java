package com.fourman.storage.application.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fourman.common.dto.response.FileResponse;
import com.fourman.storage.domain.File;
import com.fourman.storage.infrastructure.persistence.entity.FileEntity;

@Mapper(componentModel = "spring")
public interface FileResponseMapper extends DTOMapper<FileResponse, File, FileEntity> {
    List<FileResponse> listDomainModelToListDTO(List<File> domainModel);
}
