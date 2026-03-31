package com.fourman.publicinfo.location.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.location.dto.response.WardDTO;
import com.fourman.publicinfo.location.entity.WardEntity;

@Mapper(componentModel = "spring")
public interface WardMapper extends DTOMapper<WardDTO, WardEntity> {}
