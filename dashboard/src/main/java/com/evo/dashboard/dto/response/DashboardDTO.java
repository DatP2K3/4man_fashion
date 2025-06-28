package com.evo.dashboard.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDTO {
    private List<OrderStatisticByCityDTO> orderByCity;
    private List<RevenueAndOrdersDTO> revenueAndOrders;
    private SummaryTodayDTO todaySummary;
}
