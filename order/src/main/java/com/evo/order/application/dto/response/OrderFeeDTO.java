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
    private int totalQuantity;
    private Long totalPrice;
    private int shippingFee;
    private int totalWeight;
    private  int totalHeight;
    private int totalWidth;
    private int totalLength;
}
