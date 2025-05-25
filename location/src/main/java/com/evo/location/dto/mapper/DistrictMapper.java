package com.evo.location.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.location.dto.response.DistrictDTO;
import com.evo.location.entity.DistrictEntity;

@Mapper(componentModel = "spring")
public interface DistrictMapper extends DTOMapper<DistrictDTO, DistrictEntity> {}
