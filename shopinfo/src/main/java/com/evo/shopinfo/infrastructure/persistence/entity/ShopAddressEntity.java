package com.evo.shopinfo.infrastructure.persistence.entity;

import com.evo.common.Auditor;
import com.evo.common.entity.AuditEntity;
import com.evo.common.enums.ShopAddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "shop_address")
public class ShopAddressEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "shop_name")
    private String shopName;

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

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ShopAddressType type;
}
