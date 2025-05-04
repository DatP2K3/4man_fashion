package com.evo.order.application.dto.response;

import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import com.evo.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
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
    private List<OrderItemDTO> orderItems;
}
