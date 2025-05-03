package com.evo.profile.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "shipping_addresses")
public class ShippingAddressEntity {
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

    @Column(name = "ward_code")
    private String wardCode; // Mã phường/xã

    @Column(name = "district")
    private String district; // Quận/Huyện

    @Column(name = "district_id")
    private String districtId; // Id quận/huyện

    @Column(name = "city")
    private String city;

    @Column(name = "profile_id")
    private UUID profileId;

    @Column(name = "default_address")
    private Boolean defaultAddress; // Địa chỉ mặc định
}
