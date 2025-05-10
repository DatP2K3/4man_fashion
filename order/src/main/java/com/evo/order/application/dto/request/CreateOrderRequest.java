package com.evo.order.application.dto.request;

import com.evo.common.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private  UUID toAddressId;
    private PaymentMethod paymentMethod;
    private String note;
    private UUID referencesId;
}
