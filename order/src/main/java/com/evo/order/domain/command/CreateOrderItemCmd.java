package com.evo.order.domain.command;

import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateOrderItemCmd {
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
