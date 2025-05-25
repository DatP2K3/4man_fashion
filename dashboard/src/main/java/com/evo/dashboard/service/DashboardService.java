package com.evo.dashboard.service;

import com.evo.common.enums.DashboardTime;
import com.evo.dashboard.dto.response.DashboardDTO;

public interface DashboardService {
    DashboardDTO getDashboardData(DashboardTime dashboardTime);
}
