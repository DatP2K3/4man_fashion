package com.fourman.publicinfo.location.dto.mapper;

import org.mapstruct.Mapper;

import com.fourman.publicinfo.location.dto.response.ProvinceDTO;
import com.fourman.publicinfo.location.entity.ProvinceEntity;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends DTOMapper<ProvinceDTO, ProvinceEntity> {}
