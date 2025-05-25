package com.evo.location.service;

import java.util.List;

import com.evo.location.dto.response.DistrictDTO;
import com.evo.location.dto.response.ProvinceDTO;
import com.evo.location.dto.response.WardDTO;

public interface LocationQueryService {
    List<ProvinceDTO> getAllProvinces();

    List<DistrictDTO> getDistrictsByProvinceId(int provinceId);

    List<WardDTO> getWardsByDistrictId(int districtId);
}
