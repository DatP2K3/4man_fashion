package com.fourman.storage.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.storage.domain.File;
import com.fourman.storage.infrastructure.persistence.entity.FileEntity;

@Mapper(componentModel = "Spring")
public interface FileEntityMapper extends EntityMapper<File, FileEntity> {}
