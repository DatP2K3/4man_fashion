package com.evo.profile.application.dto.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateProfileRequest {
    private UUID id;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)
    private String ward; // Phường/Xã
    private String district; // Quận/Huyện
    private String city;
}
