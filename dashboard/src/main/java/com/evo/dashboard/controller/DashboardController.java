package com.evo.dashboard.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
public class DashboardController {
    private final DashboardService dashboardService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ApiResponses<DashboardDTO> getDashboard(@RequestParam DashboardTime dashboardTime) {
        DashboardDTO dashboardDTO = dashboardService.getDashboardData(dashboardTime);
        return ApiResponses.<DashboardDTO>builder()
                .data(dashboardDTO)
                .success(true)
                .code(200)
                .message("Get dashboard successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
