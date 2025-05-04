package com.evo.order.application.dto.request;

import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemRequest {
    private UUID userId;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String ward;
    private String wardCode;
    private String district;
    private String districtId;
    private String city;
    private int shipmentFee;
    private Long totalPrice;
    private String rejectReason;
    private String note;
}
