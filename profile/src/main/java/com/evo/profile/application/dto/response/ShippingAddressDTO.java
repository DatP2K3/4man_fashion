package com.evo.profile.application.dto.response;

import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ShippingAddressDTO {
    private UUID id;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)
    private String ward; // Phường/Xã
    private String district; // Quận/Huyện
    private String city;
    private UUID userId;
    private Boolean defaultAddress; // Địa chỉ mặc định
}
