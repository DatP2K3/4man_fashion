package com.fourman.location.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.location.dto.response.DistrictDTO;
import com.fourman.location.entity.DistrictEntity;

@Mapper(componentModel = "spring")
public interface DistrictMapper extends DTOMapper<DistrictDTO, DistrictEntity> {}
