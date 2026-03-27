package com.fourman.location.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourman.location.dto.mapper.DistrictMapper;
import com.fourman.location.dto.mapper.ProvinceMapper;
import com.fourman.location.dto.mapper.WardMapper;
import com.fourman.location.dto.response.DistrictDTO;
import com.fourman.location.dto.response.ProvinceDTO;
import com.fourman.location.dto.response.WardDTO;
import com.fourman.location.entity.DistrictEntity;
import com.fourman.location.entity.ProvinceEntity;
import com.fourman.location.entity.WardEntity;
import com.fourman.location.repository.DistrictEntityRepository;
import com.fourman.location.repository.ProvinceEntityRepository;
import com.fourman.location.repository.WardEntityRepository;

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
