package com.fourman.dashboard.service;

import com.fourman.common.enums.DashboardTime;
import com.fourman.dashboard.dto.response.DashboardDTO;

public interface DashboardService {
    DashboardDTO getDashboardData(DashboardTime dashboardTime);
}
