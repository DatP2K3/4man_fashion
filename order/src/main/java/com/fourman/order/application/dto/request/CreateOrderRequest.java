package com.fourman.order.application.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import com.fourman.common.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    @NotNull
    private UUID toAddressId;

    @NotNull
    private PaymentMethod paymentMethod;

    private String note;
    private UUID referencesId;
}
