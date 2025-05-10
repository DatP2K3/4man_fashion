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
    private String orderCode;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String fromName;
    private String fromPhoneNumber;
    private String fromAddressLine1;
    private String fromAddressLine2;
    private String fromWard;
    private String fromWardCode;
    private String fromDistrict;
    private String fromDistrictId;
    private String fromCity;
    private String toName;
    private String toPhoneNumber;
    private String toAddressLine1;
    private String toAddressLine2;
    private String toWard;
    private String toWardCode;
    private String toDistrict;
    private String toDistrictId;
    private String toCity;
    private String returnName;
    private String returnPhoneNumber;
    private String returnAddressLine1;
    private String returnAddressLine2;
    private String returnWard;
    private String returnWardCode;
    private String returnDistrict;
    private String returnDistrictId;
    private String returnCity;
    private int totalProductVariant;
    private int shipmentFee;
    private Long totalPrice;
    private String rejectReason;
    private String note;
    private UUID referencesId;
    private int totalWeight;
    private  int totalHeight;
    private int totalWidth;
    private int totalLength;
    private Boolean printed;
    private String paymentUrl;
    private String GHNOrderCode;

    private List<OrderItemDTO> orderItems;
}
