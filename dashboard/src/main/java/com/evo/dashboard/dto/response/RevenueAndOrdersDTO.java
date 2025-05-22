package com.evo.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueAndOrderTrendDTO {
    private String dateLabel;
    private Long totalRevenue;
    private Long totalOrders;
}
