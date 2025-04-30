package com.evo.profile.domain;

import java.util.UUID;

import com.evo.profile.domain.command.CreateOrUpdateShippingAddressCmd;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class ShippingAddress {
    private UUID id;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1; // Địa chỉ cụ thể (Số nhà, tên đường)
    private String addressLine2; // Địa chỉ bổ sung (Tòa nhà, căn hộ)
    private String ward; // Phường/Xã
    private String district; // Quận/Huyện
    private String city;
    private UUID profileId;
    private Boolean defaultAddress;

    public ShippingAddress(CreateOrUpdateShippingAddressCmd cmd) {
        this.recipientName = cmd.getRecipientName();
        this.phoneNumber = cmd.getPhoneNumber();
        this.addressLine1 = cmd.getAddressLine1();
        this.addressLine2 = cmd.getAddressLine2();
        this.ward = cmd.getWard();
        this.district = cmd.getDistrict();
        this.city = cmd.getCity();
        this.profileId = cmd.getProfileId();
        if (cmd.getDefaultAddress() != null) {
            this.defaultAddress = cmd.getDefaultAddress();
        } else {
            this.defaultAddress = false;
        }
    }
}
