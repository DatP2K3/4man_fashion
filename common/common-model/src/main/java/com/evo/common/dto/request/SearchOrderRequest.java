package com.evo.common.dto.request;

import java.time.Instant;
import java.util.UUID;

import com.evo.common.enums.OrderStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchOrderRequest extends PagingRequest {
    private String keyword;
    private UUID userId;
    private OrderStatus orderStatus;
    private Instant startDate;
    private Instant endDate;
    private Boolean printed;
    // TODO: Add more fields for searching (kiểm tra in chưa)
}
