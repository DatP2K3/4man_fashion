package com.fourman.publicinfo.location.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.location.dto.response.DistrictDTO;
import com.fourman.publicinfo.location.entity.DistrictEntity;

@Mapper(componentModel = "spring")
public interface DistrictMapper extends DTOMapper<DistrictDTO, DistrictEntity> {}
