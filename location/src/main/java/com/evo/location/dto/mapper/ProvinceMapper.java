package com.evo.location.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.location.dto.response.ProvinceDTO;
import com.evo.location.entity.ProvinceEntity;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends DTOMapper<ProvinceDTO, ProvinceEntity> {}
