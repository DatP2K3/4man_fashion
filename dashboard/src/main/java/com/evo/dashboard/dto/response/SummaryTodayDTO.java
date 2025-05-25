package com.evo.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryTodayDTO {
    private Long totalRevenue;
    private Long totalOrders;
    private Long totalNewUsers;
}
