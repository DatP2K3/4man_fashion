package com.evo.location.rest;

import com.evo.common.dto.response.ApiResponses;
import com.evo.location.application.dto.response.DistrictDTO;
import com.evo.location.application.dto.response.ProvinceDTO;
import com.evo.location.application.dto.response.WardDTO;
import com.evo.location.application.service.LocationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {
    private final LocationQueryService locationQueryService;

    @GetMapping("/locations/provinces")
    public ApiResponses<List<ProvinceDTO>> getProvinces() {
        List<ProvinceDTO> provinces = locationQueryService.getAllProvinces();
        return ApiResponses.<List<ProvinceDTO>>builder()
                .data(provinces)
                .success(true)
                .code(200)
                .message("Provinces retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/locations/districts-by-province/{provinceId}")
    public ApiResponses<List<DistrictDTO>> getDistricts(@PathVariable int provinceId) {
        List<DistrictDTO> districts = locationQueryService.getDistrictsByProvinceId(provinceId);
        return ApiResponses.<List<DistrictDTO>>builder()
                .data(districts)
                .success(true)
                .code(200)
                .message("Districts retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/locations/wards-by-district/{districtId}")
    public ApiResponses<List<WardDTO>> getWardsByDistrictId(@PathVariable int districtId) {
        List<WardDTO> wards = locationQueryService.getWardsByDistrictId(districtId);
        return ApiResponses.<List<WardDTO>>builder()
                .data(wards)
                .success(true)
                .code(200)
                .message("Wards retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
