package com.evo.dashboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.enums.DashboardTime;
import com.evo.dashboard.dto.response.DashboardDTO;
import com.evo.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboardControllerImpl implements DashboardController {
    private final DashboardService dashboardService;

    @Override
    public ApiResponses<DashboardDTO> getDashboard(@RequestParam DashboardTime dashboardTime) {
        return ApiResponses.of(this.dashboardService.getDashboardData(dashboardTime));
    }
}
