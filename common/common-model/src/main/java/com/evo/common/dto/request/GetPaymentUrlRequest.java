package com.evo.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPaymentUrlRequest {
    private Long totalPrice;
    private UUID orderId;
    private String orderCode;
}
