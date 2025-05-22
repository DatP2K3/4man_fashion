package com.evo.location.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.location.application.dto.response.DistrictDTO;
import com.evo.location.infrastructure.persistence.entity.DistrictEntity;

@Mapper(componentModel = "spring")
public interface DistrictMapper extends DTOMapper<DistrictDTO, DistrictEntity> {}
