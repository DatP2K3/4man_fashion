package com.evo.order.infrastructure.persistence.entity;

import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class OrderEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

   @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "ward")
    private String ward;

    @Column(name = "ward_code")
    private String wardCode;

    @Column(name = "district")
    private String district;

    @Column(name = "district_id")
    private String districtId;

    @Column(name = "city")
    private String city;

    @Column(name = "shipment_fee")
    private int shipmentFee;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "note")
    private String note;
}
