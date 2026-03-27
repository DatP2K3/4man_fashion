package com.fourman.location.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fourman.common.dto.response.Response;
import com.fourman.location.dto.response.DistrictDTO;
import com.fourman.location.dto.response.ProvinceDTO;
import com.fourman.location.dto.response.WardDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Location API")
@RequestMapping("/api")
@Validated
public interface LocationController {

    @Operation(summary = "Get all provinces")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/locations/provinces")
    Response<List<ProvinceDTO>> getProvinces();

    @Operation(summary = "Get districts by province")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/locations/districts-by-province/{provinceId}")
    Response<List<DistrictDTO>> getDistricts(@PathVariable int provinceId);

    @Operation(summary = "Get wards by district")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/locations/wards-by-district/{districtId}")
    Response<List<WardDTO>> getWardsByDistrictId(@PathVariable int districtId);
}
