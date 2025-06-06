package com.evo.common.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPaymentUrlRequest {
    private Long totalPrice;
    private UUID orderId;
    private String orderCode;
}
