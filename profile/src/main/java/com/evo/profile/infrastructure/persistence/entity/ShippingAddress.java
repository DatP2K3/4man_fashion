package com.evo.profile.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipping_addresses")
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address_line1")
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)

    @Column(name = "address_line2")
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)

    @Column(name = "ward")
    private String ward; // Phường/Xã

    @Column(name = "district")
    private String district; // Quận/Huyện

    @Column(name = "city")
    private String city;

    @Column(name = "user_id")
    private UUID userId;
}
