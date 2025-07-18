package com.evo.dashboard.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.evo.common.dto.response.Response;
import com.evo.common.enums.DashboardTime;
import com.evo.dashboard.dto.response.DashboardDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Dashboard API")
@RequestMapping("/api")
@Validated
public interface DashboardController {

    @Operation(summary = "Get dashboard data")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    Response<DashboardDTO> getDashboard(@RequestParam DashboardTime dashboardTime);
}
