package com.fourman.publicinfo.location.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourman.common.dto.response.Response;
import com.fourman.publicinfo.location.dto.response.DistrictDTO;
import com.fourman.publicinfo.location.dto.response.ProvinceDTO;
import com.fourman.publicinfo.location.dto.response.WardDTO;
import com.fourman.publicinfo.location.service.LocationQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationControllerImpl implements LocationController {
    private final LocationQueryService locationQueryService;

    @Override
    public Response<List<ProvinceDTO>> getProvinces() {
        return Response.of(this.locationQueryService.getAllProvinces());
    }

    @Override
    public Response<List<DistrictDTO>> getDistricts(@PathVariable int provinceId) {
        return Response.of(this.locationQueryService.getDistrictsByProvinceId(provinceId));
    }

    @Override
    public Response<List<WardDTO>> getWardsByDistrictId(@PathVariable int districtId) {
        return Response.of(this.locationQueryService.getWardsByDistrictId(districtId));
    }
}
