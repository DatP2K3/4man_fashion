package com.fourman.storage.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.storage.domain.FileHistory;
import com.fourman.storage.infrastructure.persistence.entity.FileHistoryEntity;

@Mapper(componentModel = "Spring")
public interface FileHistoryEntityMapper extends EntityMapper<FileHistory, FileHistoryEntity> {}
