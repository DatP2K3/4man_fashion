package com.evo.location.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.location.dto.response.WardDTO;
import com.evo.location.entity.WardEntity;

@Mapper(componentModel = "spring")
public interface WardMapper extends DTOMapper<WardDTO, WardEntity> {}
