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
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private String name;
    private int quantity;
    private Long price;
    private int weight;
    private  int height;
    private int width;
    private int length;
}
