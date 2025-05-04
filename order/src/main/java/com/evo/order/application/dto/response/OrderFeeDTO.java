package com.evo.order.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFeeDTO {
    int totalQuantity;
    Long totalPrice;
    int shippingFee;
}
