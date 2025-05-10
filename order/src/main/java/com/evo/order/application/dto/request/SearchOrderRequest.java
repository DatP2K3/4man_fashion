package com.evo.order.application.dto.request;

import com.evo.common.dto.request.PagingRequest;
import com.evo.common.enums.OrderStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrderRequest extends PagingRequest {
    private String keyword;
    private UUID userId;
    private OrderStatus orderStatus;
    //TODO: Add more fields for searching (kiểm tra in chưa)
}
