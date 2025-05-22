package com.evo.location.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evo.location.application.dto.mapper.DistrictMapper;
import com.evo.location.application.dto.mapper.ProvinceMapper;
import com.evo.location.application.dto.mapper.WardMapper;
import com.evo.location.application.dto.response.DistrictDTO;
import com.evo.location.application.dto.response.ProvinceDTO;
import com.evo.location.application.dto.response.WardDTO;
import com.evo.location.infrastructure.persistence.entity.DistrictEntity;
import com.evo.location.infrastructure.persistence.entity.ProvinceEntity;
import com.evo.location.infrastructure.persistence.entity.WardEntity;
import com.evo.location.infrastructure.persistence.repository.DistrictEntityRepository;
import com.evo.location.infrastructure.persistence.repository.ProvinceEntityRepository;
import com.evo.location.infrastructure.persistence.repository.WardEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationQueryServiceImpl implements LocationQueryService {
    private final ProvinceMapper provinceMapper;
    private final DistrictMapper districtMapper;
    private final WardMapper wardMapper;
    private final ProvinceEntityRepository provinceEntityRepository;
    private final DistrictEntityRepository districtEntityRepository;
    private final WardEntityRepository wardEntityRepository;

    @Override
    public List<ProvinceDTO> getAllProvinces() {
        List<ProvinceEntity> provinceEntities = provinceEntityRepository.findAll();
        return provinceMapper.entityListToDTOList(provinceEntities);
    }

    @Override
    public List<DistrictDTO> getDistrictsByProvinceId(int provinceId) {
        List<DistrictEntity> districtEntities = districtEntityRepository.getDistrictsByProvinceId(provinceId);
        return districtMapper.entityListToDTOList(districtEntities);
    }

    @Override
    public List<WardDTO> getWardsByDistrictId(int districtId) {
        List<WardEntity> wardEntities = wardEntityRepository.getWardsByDistrictId(districtId);
        return wardMapper.entityListToDTOList(wardEntities);
    }
}
