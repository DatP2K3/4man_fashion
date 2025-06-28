package com.evo.location.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evo.common.dto.response.ApiResponses;
import com.evo.location.dto.response.DistrictDTO;
import com.evo.location.dto.response.ProvinceDTO;
import com.evo.location.dto.response.WardDTO;
import com.evo.location.service.LocationQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {
    private final LocationQueryService locationQueryService;

    @Override
    public ApiResponses<List<ProvinceDTO>> getProvinces() {
        return ApiResponses.of(this.locationQueryService.getAllProvinces());
    }

    @Override
    public ApiResponses<List<DistrictDTO>> getDistricts(@PathVariable int provinceId) {
        return ApiResponses.of(this.locationQueryService.getDistrictsByProvinceId(provinceId));
    }

    @Override
    public ApiResponses<List<WardDTO>> getWardsByDistrictId(@PathVariable int districtId) {
        return ApiResponses.of(this.locationQueryService.getWardsByDistrictId(districtId));
    }
}
