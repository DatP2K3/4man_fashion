package com.evo.order.domain;

import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.order.domain.command.CreateOrderItemCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class OrderItem extends Auditor {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private UUID productVariantId;
    private int quantity;
    private Long price;
    private int weight;
    private int height;
    private int width;
    private int length;
    private Boolean deleted;

    public OrderItem(CreateOrderItemCmd createOrderItemCmd) {
        this.orderId = createOrderItemCmd.getOrderId();
        this.productId = createOrderItemCmd.getProductId();
        this.productVariantId = createOrderItemCmd.getProductVariantId();
        this.quantity = createOrderItemCmd.getQuantity();
        this.price = createOrderItemCmd.getPrice();
        this.weight = createOrderItemCmd.getWeight();
        this.height = createOrderItemCmd.getHeight();
        this.width = createOrderItemCmd.getWidth();
        this.length = createOrderItemCmd.getLength();
        this.deleted = false;
    }
}
