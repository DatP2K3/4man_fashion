package com.fourman.location.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.location.dto.response.WardDTO;
import com.fourman.location.entity.WardEntity;

@Mapper(componentModel = "spring")
public interface WardMapper extends DTOMapper<WardDTO, WardEntity> {}
