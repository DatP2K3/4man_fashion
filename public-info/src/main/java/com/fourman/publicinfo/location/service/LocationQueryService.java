package com.fourman.publicinfo.location.service;

import java.util.List;

import com.fourman.publicinfo.location.dto.response.DistrictDTO;
import com.fourman.publicinfo.location.dto.response.ProvinceDTO;
import com.fourman.publicinfo.location.dto.response.WardDTO;

public interface LocationQueryService {
    List<ProvinceDTO> getAllProvinces();

    List<DistrictDTO> getDistrictsByProvinceId(int provinceId);

    List<WardDTO> getWardsByDistrictId(int districtId);
}
