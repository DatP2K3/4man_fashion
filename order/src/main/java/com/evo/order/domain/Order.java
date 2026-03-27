package com.evo.order.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.evo.common.Auditor;
import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import com.evo.common.support.IdUtils;
import com.evo.order.domain.command.CreateOrderCmd;
import com.evo.order.infrastructure.support.exception.OrderCodeUtils;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class Order extends Auditor {
    private UUID id;
    private String orderCode;
    private UUID userId;
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
    private Long cashbackUsed;
    private String rejectReason;
    private String note;
    private UUID referencesId;
    private int totalWeight;
    private int totalHeight;
    private int totalWidth;
    private int totalLength;
    private String paymentUrl;
    private Boolean printed;
    private String GHNOrderCode;
    private List<OrderItem> orderItems;

    public Order(CreateOrderCmd createOrderCmd) {
        this.id = IdUtils.nextId();
        this.orderCode = OrderCodeUtils.getRandomNumber(8);
        this.userId = createOrderCmd.getUserId();
        this.paymentMethod = createOrderCmd.getPaymentMethod();
        if (this.paymentMethod.equals(PaymentMethod.COD)) {
            this.orderStatus = OrderStatus.PENDING_SHIPMENT;
        } else {
            this.orderStatus = OrderStatus.UNPAID;
        }
        this.paymentStatus = PaymentStatus.UNPAID;

        // From address
        this.fromName = createOrderCmd.getFromName();
        this.fromPhoneNumber = createOrderCmd.getFromPhoneNumber();
        this.fromAddressLine1 = createOrderCmd.getFromAddressLine1();
        this.fromAddressLine2 = createOrderCmd.getFromAddressLine2();
        this.fromWard = createOrderCmd.getFromWard();
        this.fromWardCode = createOrderCmd.getFromWardCode();
        this.fromDistrict = createOrderCmd.getFromDistrict();
        this.fromDistrictId = createOrderCmd.getFromDistrictId();
        this.fromCity = createOrderCmd.getFromCity();

        // To address
        this.toName = createOrderCmd.getToName();
        this.toPhoneNumber = createOrderCmd.getToPhoneNumber();
        this.toAddressLine1 = createOrderCmd.getToAddressLine1();
        this.toAddressLine2 = createOrderCmd.getToAddressLine2();
        this.toWard = createOrderCmd.getToWard();
        this.toWardCode = createOrderCmd.getToWardCode();
        this.toDistrict = createOrderCmd.getToDistrict();
        this.toDistrictId = createOrderCmd.getToDistrictId();
        this.toCity = createOrderCmd.getToCity();

        // Return address
        this.returnName = createOrderCmd.getReturnName();
        this.returnPhoneNumber = createOrderCmd.getReturnPhoneNumber();
        this.returnAddressLine1 = createOrderCmd.getReturnAddressLine1();
        this.returnAddressLine2 = createOrderCmd.getReturnAddressLine2();
        this.returnWard = createOrderCmd.getReturnWard();
        this.returnWardCode = createOrderCmd.getReturnWardCode();
        this.returnDistrict = createOrderCmd.getReturnDistrict();
        this.returnDistrictId = createOrderCmd.getReturnDistrictId();
        this.returnCity = createOrderCmd.getReturnCity();

        this.shipmentFee = createOrderCmd.getShipmentFee();
        this.totalPrice = createOrderCmd.getTotalPrice();
        this.cashbackUsed = createOrderCmd.getCashbackUsed();
        this.rejectReason = createOrderCmd.getRejectReason();
        this.note = createOrderCmd.getNote();
        this.totalWeight = createOrderCmd.getTotalWeight();
        this.totalHeight = createOrderCmd.getTotalHeight();
        this.totalWidth = createOrderCmd.getTotalWidth();
        this.totalLength = createOrderCmd.getTotalLength();
        this.printed = false;
        this.referencesId = createOrderCmd.getReferencesId();
        this.orderItems = new ArrayList<>();
        createOrderCmd.getOrderItems().forEach(createOrderItemCmd -> {
            createOrderItemCmd.setOrderId(this.id);
            OrderItem newOrderItem = new OrderItem(createOrderItemCmd);
            this.orderItems.add(newOrderItem);
        });
    }

    // === Domain Methods ===

    /**
     * Assign payment URL for online payment orders.
     */
    public void assignPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    /**
     * Cancel this order. Validates that only PENDING_SHIPMENT or WAITING_FOR_PICKUP orders can be cancelled.
     * Also soft-deletes all order items.
     */
    public void cancel() {
        if (this.orderStatus != OrderStatus.PENDING_SHIPMENT && this.orderStatus != OrderStatus.WAITING_FOR_PICKUP) {
            throw new IllegalStateException("Cannot cancel order with status: " + this.orderStatus);
        }
        this.orderStatus = OrderStatus.CANCELLED;
        if (this.orderItems != null) {
            this.orderItems.forEach(OrderItem::markAsDeleted);
        }
    }

    /**
     * Check if this order is cancellable.
     */
    public boolean isCancellable() {
        return this.orderStatus == OrderStatus.PENDING_SHIPMENT || this.orderStatus == OrderStatus.WAITING_FOR_PICKUP;
    }

    /**
     * Mark payment as successful — transitions to PENDING_SHIPMENT.
     */
    public void markPaymentSuccess() {
        this.paymentStatus = PaymentStatus.PAID;
        this.orderStatus = OrderStatus.PENDING_SHIPMENT;
    }

    /**
     * Mark payment as failed.
     */
    public void markPaymentFailed() {
        this.paymentStatus = PaymentStatus.FAILED;
    }

    /**
     * Assign GHN shipping order and transition status to WAITING_FOR_PICKUP.
     */
    public void assignShipping(String ghnOrderCode) {
        this.GHNOrderCode = ghnOrderCode;
        this.orderStatus = OrderStatus.WAITING_FOR_PICKUP;
    }

    /**
     * Mark this order as printed.
     */
    public void markAsPrinted() {
        this.printed = true;
    }

    /**
     * Update order status from external tracking (CronJob sync).
     * Returns true if status actually changed.
     */
    public boolean updateStatusFromTracking(OrderStatus newStatus) {
        if (newStatus == null || this.orderStatus.equals(newStatus)) {
            return false;
        }
        this.orderStatus = newStatus;
        return true;
    }

    /**
     * Used by infrastructure layer to enrich order with its items after loading from DB.
     */
    public void enrichOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
