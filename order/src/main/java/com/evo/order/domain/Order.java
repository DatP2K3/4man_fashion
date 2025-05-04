package com.evo.order.domain;

import com.evo.common.Auditor;
import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Order extends Auditor {
    private UUID id;
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
    private List<OrderItem> orderItems;
}
