package com.evo.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyOrderStatisDTO {
    private long totalRevenue;
    private long totalOrders;
}
