package com.evo.location.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.location.application.dto.response.WardDTO;
import com.evo.location.infrastructure.persistence.entity.WardEntity;

@Mapper(componentModel = "spring")
public interface WardMapper extends DTOMapper<WardDTO, WardEntity> {}
