package com.evo.order.application.dto.request;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.evo.common.dto.request.PagingRequest;
import com.evo.common.enums.OrderStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrderRequest extends PagingRequest {
    private String keyword;
    private UUID userId;
    private OrderStatus orderStatus;
    private Instant startDate;
    private Instant endDate;
    private Boolean printed;
    // TODO: Add more fields for searching (kiểm tra in chưa)
}
