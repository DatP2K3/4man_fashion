package com.fourman.location.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.location.dto.response.ProvinceDTO;
import com.fourman.location.entity.ProvinceEntity;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends DTOMapper<ProvinceDTO, ProvinceEntity> {}
