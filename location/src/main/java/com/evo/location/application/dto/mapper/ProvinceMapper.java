package com.evo.location.application.dto.mapper;

import com.evo.location.application.dto.response.ProvinceDTO;
import com.evo.location.infrastructure.persistence.entity.ProvinceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends DTOMapper<ProvinceDTO, ProvinceEntity> {}