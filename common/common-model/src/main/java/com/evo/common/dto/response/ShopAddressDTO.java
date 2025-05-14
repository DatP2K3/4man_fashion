package com.evo.common.dto.response;

import java.util.UUID;

import com.evo.common.enums.ShopAddressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopAddressDTO {
    private UUID id;
    private String shopName;
    private String phoneNumber;
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)
    private String ward; // Phường/Xã
    private String wardCode; // Mã phường/xã
    private String district; // Quận/Huyện
    private String districtId; // Id quận/huyện
    private String city;
    private ShopAddressType type;
}
