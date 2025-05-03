package com.evo.location.application.dto.mapper;

import com.evo.location.application.dto.response.DistrictDTO;
import com.evo.location.infrastructure.persistence.entity.DistrictEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper extends DTOMapper<DistrictDTO, DistrictEntity> {
}
