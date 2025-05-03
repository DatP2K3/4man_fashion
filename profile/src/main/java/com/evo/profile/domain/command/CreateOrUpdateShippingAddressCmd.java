package com.evo.profile.domain.command;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateShippingAddressCmd {
    private UUID id;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)
    private String ward; // Phường/Xã
    private String district; // Quận/Huyện
    private String wardCode; // Mã phường/xã
    private String districtId; // Id quận/huyện
    private String city;
    private Boolean defaultAddress; // Địa chỉ mặc định
    private UUID profileId;
}
