package com.evo.location.application.service;

import java.util.List;

import com.evo.location.application.dto.response.DistrictDTO;
import com.evo.location.application.dto.response.ProvinceDTO;
import com.evo.location.application.dto.response.WardDTO;

public interface LocationQueryService {
    List<ProvinceDTO> getAllProvinces();

    List<DistrictDTO> getDistrictsByProvinceId(int provinceId);

    List<WardDTO> getWardsByDistrictId(int districtId);
}
