package com.evo.location.application.dto.mapper;

import org.mapstruct.Mapper;

import com.evo.location.application.dto.response.ProvinceDTO;
import com.evo.location.infrastructure.persistence.entity.ProvinceEntity;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends DTOMapper<ProvinceDTO, ProvinceEntity> {}
