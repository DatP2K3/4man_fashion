package com.evo.order.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.OrderStatus;
import com.evo.common.enums.PaymentMethod;
import com.evo.common.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class OrderEntity extends AuditEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // From address fields
    @Column(name = "from_name")
    private String fromName;

    @Column(name = "from_phone_number")
    private String fromPhoneNumber;

    @Column(name = "from_address_line1")
    private String fromAddressLine1;

    @Column(name = "from_address_line2")
    private String fromAddressLine2;

    @Column(name = "from_ward")
    private String fromWard;

    @Column(name = "from_ward_code")
    private String fromWardCode;

    @Column(name = "from_district")
    private String fromDistrict;

    @Column(name = "from_district_id")
    private String fromDistrictId;

    @Column(name = "from_city")
    private String fromCity;

    // To address fields
    @Column(name = "to_name")
    private String toName;

    @Column(name = "to_phone_number")
    private String toPhoneNumber;

    @Column(name = "to_address_line1")
    private String toAddressLine1;

    @Column(name = "to_address_line2")
    private String toAddressLine2;

    @Column(name = "to_ward")
    private String toWard;

    @Column(name = "to_ward_code")
    private String toWardCode;

    @Column(name = "to_district")
    private String toDistrict;

    @Column(name = "to_district_id")
    private String toDistrictId;

    @Column(name = "to_city")
    private String toCity;

    // Return address fields
    @Column(name = "return_name")
    private String returnName;

    @Column(name = "return_phone_number")
    private String returnPhoneNumber;

    @Column(name = "return_address_line1")
    private String returnAddressLine1;

    @Column(name = "return_address_line2")
    private String returnAddressLine2;

    @Column(name = "return_ward")
    private String returnWard;

    @Column(name = "return_ward_code")
    private String returnWardCode;

    @Column(name = "return_district")
    private String returnDistrict;

    @Column(name = "return_district_id")
    private String returnDistrictId;

    @Column(name = "return_city")
    private String returnCity;

    @Column(name = "total_product_variant")
    private Integer totalProductVariant;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "cashback_used")
    private Long cashbackUsed;

    @Column(name = "reject_reason", columnDefinition = "TEXT")
    private String rejectReason;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "references_Id")
    private UUID referencesId;

    @Column(name = "total_weight")
    private Integer totalWeight;

    @Column(name = "total_height")
    private Integer totalHeight;

    @Column(name = "total_width")
    private Integer totalWidth;

    @Column(name = "total_length")
    private Integer totalLength;

    @Column(name = "printed")
    private Boolean printed;

    @Column(name = "ghn_order_code")
    private String GHNOrderCode;

    @Column(name = "payment_url", columnDefinition = "TEXT")
    private String paymentUrl;

    @Column(name = "shipment_fee")
    private Integer shipmentFee;
}
