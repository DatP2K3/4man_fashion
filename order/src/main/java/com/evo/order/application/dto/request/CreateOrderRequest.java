package com.evo.order.application.dto.request;

import java.util.UUID;

import com.evo.common.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private UUID toAddressId;
    private PaymentMethod paymentMethod;
    private String note;
    private UUID referencesId;
}
