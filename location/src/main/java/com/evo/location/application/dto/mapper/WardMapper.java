package com.evo.location.application.dto.mapper;

import com.evo.location.application.dto.response.WardDTO;
import com.evo.location.infrastructure.persistence.entity.WardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WardMapper extends DTOMapper<WardDTO, WardEntity> {
}
